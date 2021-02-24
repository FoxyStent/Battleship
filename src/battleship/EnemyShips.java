package battleship;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EnemyShips implements Initializable {
    public Pane paneCar;
    public Pane paneBat;
    public Pane paneCru;
    public Pane paneSub;
    public Pane paneDes;
    public Label hpCar;
    public Label hpBat;
    public Label hpCru;
    public Label hpSub;
    public Label hpDes;

    private NPC enemy;

    public void setEnemy(NPC en){
        enemy = en;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            Label[] labels = {hpCar, hpBat, hpCru, hpSub, hpDes};
            Pane[] panes = {paneCar, paneBat, paneCru, paneSub, paneDes};
            Ship[] ships = enemy.getPlayerShips();
            for (int i=0; i<5; i++){
                Ship s = ships[i];
                int size = s.getSize();
                int hpleft = s.getHpLeft();
                String st = s.getState();
                labels[i].setText(hpleft+"/"+size);
                Label l = (Label)panes[i].getChildren().get(0);
                l.setText(st);
                if (st.equals("Untouched"))
                    panes[i].setStyle("-fx-background-color: green");
                else if (st.equals("Sunk"))
                    panes[i].setStyle("-fx-background-color: red");
                else
                    panes[i].setStyle("-fx-background-color: orange");
            }
        });
    }

    public void closeWin(ActionEvent actionEvent) {
        Stage win = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        win.close();
    }
}
