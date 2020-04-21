package client.view;

import client.controller.ClientController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChat extends Application {
    private ClientController controller;
    private ClientChatController chatController;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public ClientChat(ClientController controller) {
        this.controller = controller;
        //launch();
    }

    public ClientChatController getController() {
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


}
