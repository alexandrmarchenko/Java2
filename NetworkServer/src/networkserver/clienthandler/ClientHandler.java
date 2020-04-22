package networkserver.clienthandler;

import client.Command;
import client.command.AuthCommand;
import client.command.BroadcastMessageCommand;
import client.command.PrivateMessageCommand;
import networkserver.auth.AuthService;
import networkserver.server.MyServer;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private MyServer serverInstance;
    private AuthService authService;

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String nickname;

    public ClientHandler(Socket clientSocket, MyServer myServer) {
        this.clientSocket = clientSocket;
        this.serverInstance = myServer;
        this.authService = serverInstance.getAuthService();
    }

    public void handle() throws IOException {
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.out.println("Connection has been failed");
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        try {
            serverInstance.unsubscribe(this);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            switch (command.getType()) {
                case END:
                    return;
                case BROADCAST_MESSAGE:
                    BroadcastMessageCommand data = (BroadcastMessageCommand) command.getData();
                    serverInstance.broadcastMessage(Command.messageCommand(nickname, data.getMessage()));
                    break;
                case PRIVATE_MESSAGE:
                    PrivateMessageCommand privateMessageCommand = (PrivateMessageCommand) command.getData();
                    String receiver = privateMessageCommand.getReceiver();
                    String message = privateMessageCommand.getMessage();
                    serverInstance.sendPrivateMessage(receiver, command.messageCommand(nickname, message));
                    break;
                default:
                    String errorMessage = "Unknown type of command: " + command.getType();
                    System.err.println("Unknown type of command: " + command.getType());
                    sendMessage(Command.errorCommand(errorMessage));

            }
        }
    }

    private void authentication() throws IOException {
        Thread wait = new Thread(() -> {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                return;
            }
            String errorMessage = "Time for connection expired";
            System.err.println(errorMessage);
            try {
                sendMessage(Command.timeoutEndCommand(errorMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        wait.start();
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            switch (command.getType()) {
                case AUTH: {
                    if (processAuthCommand(command)) {
                        wait.interrupt();
                        return;
                    }
                    break;
                }
                default:
                    String errorMessage = "Illegal command for authentification" + command.getType();
                    System.err.println(errorMessage);
                    sendMessage(Command.errorCommand(errorMessage));
            }
        }
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Unknown type of object from client";
            System.err.println(errorMessage);
            e.printStackTrace();
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommand authCommand = (AuthCommand) command.getData();
        String login = authCommand.getLogin();
        String password = authCommand.getPassword();

        String nickname = authService.getNickByLoginAndPassword(login, password);
        if (nickname == null) {
            sendMessage(Command.authErrorCommand("incorrect login/password"));
        } else if (serverInstance.isNicknameBusy(nickname)) {
            sendMessage(Command.authErrorCommand("Nickname is already used"));
        } else {
            authCommand.setUsername(nickname);
            sendMessage(command);
            setNickname(nickname);
            serverInstance.broadcastMessage(Command.messageCommand(null, nickname + " has joined chat"));
            serverInstance.subscribe(this);
            return true;
        }
        return false;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(Command command) throws IOException {
        outputStream.writeObject(command);
    }
}
