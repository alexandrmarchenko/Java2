package client.controller;

import client.Command;
import client.model.NetworkService;
import client.view.AuthDialog;
import client.view.ClientChat;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nickname;
    private final String ALL_USERS_LIST_ITEM = "All";

    public ClientController(String serverHost, int serverPort) {
        this.networkService = new NetworkService(serverHost, serverPort, this);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
    }

    public void runApplication() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(new AuthEvent() {
            @Override
            public void authIsSuccessful(String nickname) {
                ClientController.this.openChat(nickname);
                //ClientController.this.setUserName(nickname);
            }
        });
        //authDialog.setVisible(true);
        //Application.launch(authDialog.getClass());
        new JFXPanel();
        Platform.runLater(() -> {
            try {
                authDialog.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void openChat(String nickname) {
        //authDialog.dispose();
        Platform.runLater(()->{
            try {
                authDialog.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        networkService.setMessageHandler(new Consumer<String>() {
            @Override
            public void accept(String message) {
                clientChat.getClientChatController().appendMessage(message);
            }
        });
        Platform.runLater(() -> {
            try {
                ClientController.this.setUserName(nickname);
                clientChat.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUserName(String nickname) {
        clientChat.getStage().setTitle(nickname);
        this.nickname = nickname;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect();
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        sendCommand(Command.authCommand(login, pass));
    }

    public void sendMessage(String message) {
        sendCommand(Command.broadcastMessageCommand(message));
    }

    private void sendCommand(Command command) {
        try {
            networkService.sendCommand(command);
        } catch (IOException e) {
            showErrorMessage(e.getMessage());
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    public void showErrorMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });

    }

    public void sendPrivateMessage(String username, String message) {
        sendCommand(Command.privateMessageCommand(username, message));
    }

    public void updateUserList(List<String> users) {
        while (clientChat.getClientChatController() == null) {
        }
        users.remove(nickname);
        users.add(0, ALL_USERS_LIST_ITEM);
        clientChat.updateUsers(users);
    }
}
