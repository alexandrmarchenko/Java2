package lesson4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ICQController {

    @FXML
    private Button btn;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtFldMessage;

    @FXML
    private void sendMsg(ActionEvent event) {
        txtArea.appendText(String.format("You: %s%n",txtFldMessage.getText()));
        txtFldMessage.clear();
    }

}
