package client.controller;

import client.model.NetworkService;
import client.view.AuthDialog;
import client.view.ClientChat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nickname;

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
                clientChat.getController().appendMessage(message);
            }
        });
        Platform.runLater(() -> {
            try {
                clientChat.start(new Stage());
                ClientController.this.setUserName(nickname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUserName(String nickname) {
        /*SwingUtilities.invokeLater(() -> {
            clientChat.setTitle(nickname);
        });*/
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
        networkService.sendAuthMessage(login, pass);
    }

    public void sendMessage(String message) {
        try {
            networkService.sendMessage(message);
        } catch (IOException e) {
            showErrorMessage("Failed to send message!");
            e.printStackTrace();
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
        sendMessage(String.format("/w %s %s", username, message));
    }
}
