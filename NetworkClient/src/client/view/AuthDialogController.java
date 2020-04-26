package client.view;

import client.controller.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthDialogController {

    private ClientController controller;

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    @FXML
    private Button btnAuth;

    @FXML
    private TextArea btnExit;

    @FXML
    private TextField txtFldLogin;

    @FXML
    private TextField pswdFldPassword;

    @FXML
    private void auth(ActionEvent event) {

        String login = txtFldLogin.getText();
        String pass = pswdFldPassword.getText();
        try {
            controller.sendAuthMessage(login, pass);
        } catch (IOException e) {
           controller.showErrorMessage("Ошибка при попытки аутентификации");
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    public void disableAuth() {
        btnAuth.setDisable(true);
    }
}
