package client.view;

import client.controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthDialog extends Application {

    private ClientController controller;

    private ClientChat clientChat;

    private Stage stage;

    public AuthDialog(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthDialog.fxml"));
        Parent root = loader.load();

        AuthDialogController controller = loader.getController();
        controller.setController(this.controller);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setWidth(270);
        stage.setHeight(180);
        stage.setResizable(false);
        stage.show();

        this.stage = stage;
    }

    @Override
    public void stop() {
        this.stage.close();
    }
}
