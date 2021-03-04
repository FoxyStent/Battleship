package battleship;

import battleship.exce.AdjacentTilesException;
import battleship.exce.InvalidCountException;
import battleship.exce.OverlapTilesException;
import battleship.exce.OversizeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Settings{

    private int eId;
    private int pId;

    @FXML
    private TextField e;
    @FXML
    private TextField p;

    private int[][] readAndPlace(File PlayerDescription) throws FileNotFoundException, OverlapTilesException, OversizeException {
        int [][] fakeGrid = new int[10][10];
        Scanner reader = new Scanner(PlayerDescription);
        String[] tokens;
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            tokens = data.split(",");
            int x = Integer.parseInt(tokens[1]);
            int y = Integer.parseInt(tokens[2]);
            int or = Integer.parseInt(tokens[3]);
            switch (tokens[0]) {
                case "1": {
                    for (int i=0; i<5; i++){
                        if (or == 2) {
                            if (x+i>9)
                                throw new OversizeException("Carrier Ship is out of Grid");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 1;
                        }
                        else {
                            if (y+i>9){
                                throw new OversizeException("Carrier Ship is out of Grid");
                            }
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 1;
                        }
                    }
                    continue;
                }
                case "2": {
                    for (int i=0; i<4; i++){
                        if (or == 2) {
                            if (x+i>9)
                                throw new OversizeException("Battleship Ship is out of Grid");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 2;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Battleship Ship is out of Grid");
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 2;
                        }
                    }
                    continue;
                }
                case "3": {
                    for (int i=0; i<3; i++){
                        if (or == 2) {
                            if (x+i>9)
                                throw new OversizeException("Cruiser Ship is out of Grid");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 3;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Cruiser Ship is out of Grid");
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 3;
                        }
                    }
                    continue;
                }
                case "4": {
                    for (int i=0; i<3; i++){
                        if (or == 2) {
                            if (x+i>9)
                                throw new OversizeException("Submarine Ship is out of Grid");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 4;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Submarine Ship is out of Grid");
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x][y + i] = 4;
                        }
                    }
                    continue;
                }
                case "5": {
                    for (int i=0; i<2; i++){
                        if (or == 2) {
                            if (x+i>9)
                                throw new OversizeException("Destroyer Ship is out of Grid");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 5;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Destroyer Ship is out of Grid");
                            if (fakeGrid[x][y + i] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
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
        int [] shipsSeen = new int[6];

        for(int i=0; i<10; i++) {
            for (int j = 0; j<10; j++) {
                System.out.println(i + " " + j);
                int curr = fakeGrid[i][j];
                shipsSeen[curr]++;
                if (i==9 && j==9)
                    break;
                else if (i==9)
                    if (curr != fakeGrid[i][j+1] && curr !=0 && fakeGrid[i][j+1] != 0)
                        throw new AdjacentTilesException("There is a ship Adjacent to " + i + "," + j);
                else if (j==9)
                    if (curr != fakeGrid[i+1][j] && curr !=0 && fakeGrid[i+1][j] != 0)
                        throw new AdjacentTilesException("There is a ship Adjacent to " + i + "," + j);
                else if ((curr != fakeGrid[i + 1][j] || curr != fakeGrid[i][j + 1]) && curr!=0 && fakeGrid[i + 1][j] != 0 && fakeGrid[i][j + 1] !=0  )
                    throw new AdjacentTilesException("There is a ship Adjacent to " + i + "," + j);

            }
        }
        System.out.println(Arrays.toString(shipsSeen));
        if (shipsSeen[1] != 5 || shipsSeen[2] != 4 || shipsSeen[3] != 3 || shipsSeen[4] != 3 || shipsSeen[5] != 2)
            throw new InvalidCountException("There has been a duplicate ship or ships are missing");
    }

    public boolean validatePlayer(){
        if (p.getText().length() == 0) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario not chosen.\nPlease choose one to continue");
            fileNotFound.showAndWait();
            return false;
        }
        pId = Integer.parseInt(p.getText());

        String path = "src/battleship/medialab/player_"  + pId +".txt";
        File PlayerDescription = new File(path);
        if (PlayerDescription.length() == 0){
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario is Empty.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        }
        int [][] fakeGrid;
        try {
            fakeGrid = readAndPlace(PlayerDescription);
        } catch (FileNotFoundException fileNotFoundException) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario was not Found.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        } catch (OverlapTilesException | OversizeException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + " on Player Scenario\nPlease try again!");
            overlapping.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException | InvalidCountException someException) {
            Alert something = new Alert(Alert.AlertType.ERROR, someException.getMessage() + " on Player Scenario\nPlease try again!");
            something.showAndWait();
            return false;
        }
        return true;
    }

    public boolean validateEnemy(){
        if (e.getText().length() == 0) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Enemy Scenario not chosen.\nPlease choose one to continue");
            fileNotFound.showAndWait();
            return false;
        }
        eId = Integer.parseInt(e.getText());

        String path = "src/battleship/medialab/enemy_"  + eId + ".txt";
        File PlayerDescription = new File(path);
        if (PlayerDescription.length() == 0){
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Enemy Scenario is Empty.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        }
        int [][] fakeGrid;
        try {
            fakeGrid = readAndPlace(PlayerDescription);
        } catch (FileNotFoundException fileNotFoundException) {
            Alert fileNotFound = new Alert(Alert.AlertType.ERROR, "Player Scenario was not Found.\nPlease try again!");
            fileNotFound.showAndWait();
            return false;
        } catch (OverlapTilesException | OversizeException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + " on Enemy Scenario\nPlease try again!");
            overlapping.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException | InvalidCountException someException) {
            Alert something = new Alert(Alert.AlertType.ERROR, someException.getMessage() + " on Enemy Scenario\nPlease try again!");
            something.showAndWait();
            return false;
        }
        return true;
    }

    public void Validate(ActionEvent actionEvent) {
        if(validatePlayer() && validateEnemy()) {
            BootMenu.playerScen = pId;
            BootMenu.enemyScen = eId;
            ((Stage) ((Node) actionEvent.getTarget()).getScene().getWindow()).close();
        }
    }


    public void back(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getTarget()).getScene().getWindow()).close();
    }
}
