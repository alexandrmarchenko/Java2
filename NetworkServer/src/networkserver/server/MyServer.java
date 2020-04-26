package networkserver.server;

import client.Command;
import client.command.MessageCommand;
import networkserver.auth.AuthService;
import networkserver.auth.BaseAuthService;
import networkserver.clienthandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final int port;
    private final List<ClientHandler> clients;
    private final AuthService authService;

    public MyServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.authService = new BaseAuthService();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");
            authService.start();

            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client has been connected");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                try {
                    handler.handle();
                } catch (IOException e) {
                    System.err.println("Failed to handle client connection");
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isNicknameBusy(String nickname) {
        for (ClientHandler client : clients) {
            if(client.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMessage(Command command) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(command);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users));
    }
    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users));
    }

    private List<String> getAllUsernames() {
//        return clients.stream()
//                .map(ClientHandler::getNickname)
//                .collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (ClientHandler client : clients) {
            result.add(client.getNickname());
        }
        return result;
    }

    public void sendPrivateMessage(String receiver, Command command) {
        for (ClientHandler client : clients) {
            if(client.getNickname().equals(receiver)) {
                try {
                    client.sendMessage(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public void broadcastMessageExceptSender(Command command) {
        MessageCommand messageCommand = (MessageCommand) command.getData();
        for (ClientHandler client : clients) {
            if (client.getNickname() != messageCommand.getUsername()) {
                try {
                    client.sendMessage(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
