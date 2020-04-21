package client.view;

import client.controller.ClientController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView userList;

    @FXML
    void initialize() {
        ObservableList elements = FXCollections.observableArrayList();
        elements.add("all");
        elements.add("nickname1");
        elements.add("nickname2");
        elements.add("nickname3");
        userList.setItems(elements);
    }

    @FXML
    private void appendMessage(ActionEvent event) {
        String message = txtFldMessage.getText().trim();
        if( message.isEmpty()) {
            return;
        }
        appendOwnMessage(message);

        if(userList.getSelectionModel().getSelectedIndex() < 1) {
            controller.sendMessage(message);
        } else {
            String username = userList.getSelectionModel().getSelectedItem().toString();
            controller.sendPrivateMessage(username, message);
        }

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
