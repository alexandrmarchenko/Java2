package client.view;

import client.controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClientChat extends Application {
    private ClientController controller;
    private ClientChatController chatController;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public ClientChat(ClientController controller) {
        this.controller = controller;
    }

    public ClientChatController getClientChatController() {
        return chatController;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientChat.fxml"));
        Parent root = loader.load();

        this.chatController = loader.getController();

        ClientChatController controller = loader.getController();
        controller.setController(this.controller);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("");
        stage.setMinHeight(500);
        stage.setMinWidth(550);
        stage.setWidth(500);
        stage.setHeight(550);
        stage.show();

        this.stage = stage;
    }


    public void updateUsers(List<String> users) {
        this.chatController.updateUsers(users);
    }
}
