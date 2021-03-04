package battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Stats {

    @FXML
    GridPane grid;

    @FXML
    Label move1;
    @FXML
    Label move2;
    @FXML
    Label move3;
    @FXML
    Label move4;
    @FXML
    Label move5;
    @FXML
    HBox out1;
    @FXML
    HBox out2;
    @FXML
    HBox out3;
    @FXML
    HBox out4;
    @FXML
    HBox out5;



    public void closeWin(ActionEvent actionEvent) {
        ((Stage) ((Node)(actionEvent.getTarget())).getScene().getWindow()).close();
    }

    public void setStats(Player pl) {
        LinkedList<Move> moves = pl.getLastFive();
        Label []mvs = {move1, move2, move3, move4, move5};
        HBox []outcomes = {out1, out2, out3, out4, out5};
        for (int i=0; i<5; i++){
            if (moves.size() > i){
                Move mv = moves.get(i);
                mvs[i].setText("(" + mv.getX() + "," + mv.getY() + ")");
                if (mv.hit) {
                    String hit = mv.getShipType();
                    outcomes[i].setStyle("-fx-background-color: green");
                    ((Label) (outcomes[i].getChildren().get(0))).setText("Hit " + hit);
                }
                else{
                    String hit = mv.getShipType();
                    outcomes[i].setStyle("-fx-background-color: red");
                    ((Label) (outcomes[i].getChildren().get(0))).setText("Hit " + hit);
                }
            }
            else{
                int finalI = i+2;
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == finalI);
            }
        }
    }
}
