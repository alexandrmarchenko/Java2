package client.view;

import client.controller.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientChatController {

    private ClientController controller;

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    @FXML
    private Button btn;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtFldMessage;

    @FXML
    private void appendMessage(ActionEvent event) {
        String message = txtFldMessage.getText();
        appendOwnMessage(message);
        controller.sendMessage(message);
        txtFldMessage.clear();

    }

    private void appendOwnMessage(String message) {
        appendMessage("Я: " + message);
    }

    public void appendMessage(String message) {
        txtArea.appendText(message);
        txtArea.appendText(System.lineSeparator());
    }

}
