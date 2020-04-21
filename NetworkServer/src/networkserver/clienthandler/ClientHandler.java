package networkserver.clienthandler;

import networkserver.auth.AuthService;
import networkserver.server.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static networkserver.server.MessageConstant.*;

public class ClientHandler {
    private MyServer serverInstance;
    private AuthService authService;

    private Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String nickname;

    public ClientHandler(Socket clientSocket, MyServer myServer) {
        this.clientSocket = clientSocket;
        this.serverInstance = myServer;
        this.authService = serverInstance.getAuthService();
    }

    public void handle() throws IOException {
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.out.println(String.format("User %s connection has been failed", nickname));
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        serverInstance.unsubscribe(this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(END_CMD)) {
                return;
            } else if (message.startsWith(PRIVATE_MSG_CMD)) {
                String[] parts = message.split("\\s+",3);
                String username = parts[1];
                String privateMessage = parts[2];
                serverInstance.sendPrivateMessage(nickname, buildMessage(privateMessage));
            } else {
                serverInstance.broadcastMessage(buildMessage(message));
            }
        }
    }

    private String buildMessage(String message) {
        return String.format("%s: %s", nickname, message);
    }

    private void authentication() throws IOException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(AUTH_CMD)) {
                String[] parts = message.split("\\s+");
                String login = parts[1];
                String password = parts[2];

                String nickname = authService.getNickByLoginAndPassword(login, password);
                if (nickname == null) {
                    sendMessage("incorrect login/password");

                } else if (serverInstance.isNicknameBusy(nickname)) {
                    sendMessage("Nickname is already used");
                } else {
                    sendMessage(String.format("%s %s",AUTH_SUCCESS_CMD, nickname));
                    setNickname(nickname);
                    serverInstance.broadcastMessage(nickname + " has joined chat");
                    serverInstance.subscribe(this);
                    break;
                }
            }
        }
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}
