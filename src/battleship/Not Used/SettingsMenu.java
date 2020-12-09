package battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsMenu implements Initializable {

    @FXML
    private Button closeButton;
    /*
    @FXML
    private ListView<String> playerList;
    @FXML
    private ListView<String> enemyList;
    */
    @FXML
    private TextField p;
    @FXML
    private TextField e;

    public void closeWindow(ActionEvent e) {
        Stage miniWindow = (Stage) ( (Node)e.getSource()).getScene().getWindow();
        miniWindow.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //playerList.getItems().add()
    }
}
