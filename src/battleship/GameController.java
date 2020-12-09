package battleship;

import battleship.exce.OverlapTilesException;
import battleship.exce.OversizeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    @FXML
    private Button bombBut;
    @FXML
    private TextField X_coord;
    @FXML
    private TextField Y_coord;
    @FXML
    private GridPane rightGrid;
    @FXML
    private GridPane leftGrid;
    @FXML
    private Label yourScore;

    private Player player;
    private Player enemy;



    public void dropTheBomb(ActionEvent actionEvent){
        if (X_coord.getText().equals("") || Y_coord.getText().equals("")){
            Alert coordAlert = new Alert(Alert.AlertType.ERROR, "Please Enter Coordinates Values", ButtonType.CLOSE);
            coordAlert.show();
            return;
        }
        int x = Integer.parseInt(X_coord.getText());
        int y = Integer.parseInt(Y_coord.getText());
        if (x <= 0 || x>10 || y <= 0 || y > 10){
            Alert coordAlert = new Alert(Alert.AlertType.ERROR, "Please Enter correct Coordinates Values", ButtonType.CLOSE);
            coordAlert.show();
            return;
        }
        GridTile cell = (GridTile) rightGrid.getChildren().get(20 + (x-1)*10 + (y-1));
        Move move = new Move(x,y);
        int awardedPoints = this.enemy.incomingBomb(move);
        int currPoints = Integer.parseInt(yourScore.getText());
        yourScore.setText(Integer.toString(currPoints+awardedPoints));
        cell.placeBomb();
    }

    public void showShips(Ship[] ships, Color color, boolean isEnemy) throws OverlapTilesException {
        GridPane grid;
        if (isEnemy)
            grid = rightGrid;
        else
            grid = leftGrid;
        for(int i=0; i<5; i++){
            int size = ships[i].size;
            int orientation = ships[i].orientation;
            int x = ships[i].StartingX;
            int y = ships[i].StartingY;
            for (int w=0; w<size; w++) {
                GridTile cell;
                System.out.print("y: " + Integer.toString(y) + " w: " + Integer.toString(w) + " x: " + Integer.toString(x));
                if (orientation == 1) {
                    cell = (GridTile) grid.getChildren().get(20 + (x-1)*10 + (y+w-1));
                    System.out.println(" get: " + Integer.toString(20 + (y + w) * 10 + x));
                    if (cell.isHasShip())
                        throw new OverlapTilesException("Cell " + Integer.toString(x) + "," + Integer.toString(y) + " has already Ship");
                }
                else {
                    System.out.println(" get " + Integer.toString(20 + y*10 + x + w));
                    cell = (GridTile) grid.getChildren().get(20 + (x+w-1)*10 + (y-1));
                }
                cell.setFill(color);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Now Running Initializing");
        ColumnConstraints c1 = new ColumnConstraints(20.0, -1.0, -1.0, Priority.SOMETIMES, HPos.CENTER, true);
        ColumnConstraints c0 = new ColumnConstraints(-1.0, -1.0, -1.0, Priority.SOMETIMES, HPos.CENTER, true);
        leftGrid.getColumnConstraints().addAll(c1,c0,c0,c0,c0,c0,c0,c0,c0,c0,c0);
        rightGrid.getColumnConstraints().addAll(c1,c0,c0,c0,c0,c0,c0,c0,c0,c0,c0);
        for (int i=1; i<11; i++) {
            leftGrid.add(new Label(Integer.toString(i)), i, 0);
            leftGrid.add(new Label(Integer.toString(i)), 0, i);
            rightGrid.add(new Label(Integer.toString(i)), i, 0);
            rightGrid.add(new Label(Integer.toString(i)), 0, i);
        }
        for (int i=1; i<11; i++){
            for (int j=1; j<11; j++) {
                leftGrid.add(new GridTile(i, j, false), j, i);
                rightGrid.add(new GridTile(i,j, true), j, i);
            }

        }
        try {
            player = new Player(false, BootMenu.playerScen);
            enemy = new Player(true, BootMenu.enemyScen);
        } catch (OversizeException | IOException e) {
            e.printStackTrace();
        }

        
        try {
            showShips(player.playerShips, Color.ANTIQUEWHITE, false);
            showShips(enemy.playerShips, Color.BURLYWOOD, true);
        } catch (OverlapTilesException e) {
            e.printStackTrace();
        }



    }
}
