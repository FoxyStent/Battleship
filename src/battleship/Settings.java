package battleship;

import battleship.exce.AdjacentTilesException;
import battleship.exce.InvalidCountException;
import battleship.exce.OverlapTilesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Settings{

    private int eId;
    private int pId;

    @FXML
    private TextField e;
    @FXML
    private TextField p;

    private int[][] readAndPlace(File PlayerDescription) throws FileNotFoundException, OverlapTilesException {
        int [][] fakeGrid = new int[10][10];
        Scanner reader = new Scanner(PlayerDescription);
        String[] tokens;
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            tokens = data.split(",");
            int x = Integer.parseInt(tokens[1]) + 1;
            int y = Integer.parseInt(tokens[2]) + 1;
            int or = Integer.parseInt(tokens[3]);
            switch (tokens[0]) {
                case "1": {
                    for (int i=0; i<5; i++){
                        if (or == 0) {
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 1;
                        }
                        else {
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 1;
                        }
                    }
                    continue;
                }
                case "2": {
                    for (int i=0; i<4; i++){
                        if (or == 0) {
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 2;
                        }
                        else {
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 2;
                        }
                    }
                    continue;
                }
                case "3": {
                    for (int i=0; i<3; i++){
                        if (or == 0) {
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 3;
                        }
                        else {
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 3;
                        }
                    }
                    continue;
                }
                case "4": {
                    for (int i=0; i<3; i++){
                        if (or == 0) {
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 4;
                        }
                        else {
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 4;
                        }
                    }
                    continue;
                }
                case "5": {
                    for (int i=0; i<2; i++){
                        if (or == 0) {
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 5;
                        }
                        else {
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at" + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 5;
                        }
                    }
                }
            }
        }
        reader.close();
        return fakeGrid;
    }

    private void checkGrid(int [][]fakeGrid) throws AdjacentTilesException, InvalidCountException {
        int [] shipsSeen = new int[5];

        for(int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                int curr = fakeGrid[i][j];
                shipsSeen[curr]++;
                if (curr != fakeGrid[i + 1][j] || curr != fakeGrid[i][j + 1])
                    throw new AdjacentTilesException("There is a ship Adjacent to " + i + "," + j);
            }
        }

        if (shipsSeen[0] != 5 || shipsSeen[1] != 4 || shipsSeen[2] != 3 || shipsSeen[3] != 3 || shipsSeen[4] != 2)
            throw new InvalidCountException("There has been a duplicate ship");
    }

    public boolean validatePlayer(){
        pId = Integer.parseInt(p.getText());

        String path = "src/battleship/medialab/player_"  + pId;
        File PlayerDescription = new File(path);
        int [][] fakeGrid;
        try {
            fakeGrid = readAndPlace(PlayerDescription);
        } catch (FileNotFoundException fileNotFoundException) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario was not Found.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        } catch (OverlapTilesException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + "\nPlease try again!");
            overlapping.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException | InvalidCountException someException) {
            Alert something = new Alert(Alert.AlertType.ERROR, someException.getMessage() + "\nPlease try again!");
            something.showAndWait();
            return false;
        }
        return true;
    }

    public boolean validateEnemy(){
        eId = Integer.parseInt(e.getText());

        String path = "src/battleship/medialab/enemy_"  + eId;
        File PlayerDescription = new File(path);
        int [][] fakeGrid;
        try {
            fakeGrid = readAndPlace(PlayerDescription);
        } catch (FileNotFoundException fileNotFoundException) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario was not Found.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        } catch (OverlapTilesException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + "\nPlease try again!");
            overlapping.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException | InvalidCountException someException) {
            Alert something = new Alert(Alert.AlertType.ERROR, someException.getMessage() + "\nPlease try again!");
            something.showAndWait();
            return false;
        }
        return true;
    }

    public void Validate(ActionEvent actionEvent) {
        if(validatePlayer() && validateEnemy()) {
            BootMenu.playerScen = pId;
            BootMenu.enemyScen = eId;
        }
    }

    /*
    public static String display() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Settings");
        window.setMinWidth(640);
        window.setMinHeight(480);

        VBox main = new VBox();
        HBox pBox = new HBox();
        HBox eBox = new HBox();
        TextField e = new TextField();
        TextField p = new TextField();
        Label eLabel = new Label("Enemy Scenario:");
        Label pLabel = new Label("Player Scenario:");
        Label mainLabel = new Label("Settings");
        pBox.getChildren().addAll(pLabel, p);
        eBox.getChildren().addAll(eLabel, e);
        main.getChildren().addAll(mainLabel, pBox, eBox);

        Scene scene = new Scene(main);
        window.setScene(scene);
        window.showAndWait();

        pId = p.getText();
        eId = e.getText();
        return pId + "," + eId;
    }
     */


}
