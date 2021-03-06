package battleship;

import battleship.exce.AdjacentTilesException;
import battleship.exce.InvalidCountException;
import battleship.exce.OverlapTilesException;
import battleship.exce.OversizeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                                throw new OversizeException("Ship gets out of Grid.Carrier ship exceeds the grid horizontally");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 1;
                        }
                        else {
                            if (y+i>9){
                                throw new OversizeException("Ship gets out of Grid.Carrier ship exceeds the grid vertically");
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
                                throw new OversizeException("Ship gets out of Grid.Carrier ship exceeds the grid horizontally");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 2;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Ship gets out of Grid.Battleship exceeds the grid vertically");
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
                                throw new OversizeException("Ship gets out of Grid.Carrier exceeds the grid horizontally");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 3;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Ship gets out of Grid.Carrier exceeds the grid vertically");
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
                                throw new OversizeException("Ship gets out of Grid.Submarine exceeds the grid horizontally");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 4;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Ship gets out of Grid.Submarine exceeds the grid vertically");
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
                                throw new OversizeException("Ship gets out of Grid.Destroyer exceeds the grid horizontally");
                            if (fakeGrid[x + i][y] != 0)
                                throw new OverlapTilesException("Overlapping happens at " + (x + i) + "," + y);
                            else
                                fakeGrid[x + i][y] = 5;
                        }
                        else {
                            if (y+i>9)
                                throw new OversizeException("Ship gets out of Grid.Destroyer exceeds the grid vertically");
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
                int curr = fakeGrid[i][j];
                shipsSeen[curr]++;
                if (i==9 && j==9)
                    break;
                else if (i==9){
                    if (curr != fakeGrid[i][j + 1] && curr != 0 && fakeGrid[i][j + 1] != 0)
                        throw new AdjacentTilesException("There is a ship Adjacent to Ship Type Number " + curr);
                }
                else if (j==9){
                    if (curr != fakeGrid[i + 1][j] && curr != 0 && fakeGrid[i + 1][j] != 0)
                        throw new AdjacentTilesException("There is a ship Adjacent to Ship Type Number " + curr);
                }
                else if ((curr != fakeGrid[i + 1][j] || curr != fakeGrid[i][j + 1]) && curr!=0 && fakeGrid[i + 1][j] != 0 && fakeGrid[i][j + 1] !=0)
                    throw new AdjacentTilesException("There is a ship Adjacent to Ship Type Number " + curr);

            }
        }
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
        } catch (OverlapTilesException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + " on Player Scenario\nPlease try again!");
            overlapping.setHeaderText("Overlapping Happens");
            overlapping.showAndWait();
            return false;
        } catch (OversizeException oversizeException){
            String[] error = oversizeException.getMessage().split("\\.");
            Alert oversize = new Alert(Alert.AlertType.ERROR);
            oversize.setHeaderText(error[0]);
            oversize.setContentText(error[1] + " on Player Scenario");
            oversize.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException adjacentTilesException) {
            Alert something = new Alert(Alert.AlertType.ERROR, adjacentTilesException.getMessage() + " on Player Scenario\nPlease try again!");
            something.setHeaderText("Adjacent Ships Error");
            something.showAndWait();
            return false;
        } catch (InvalidCountException invalidCountException){
            Alert count = new Alert(Alert.AlertType.ERROR);
            count.setHeaderText("Duplicate/Missing Ships Error");
            count.setContentText("The above error occured on Player Scenario.\nPlease try again!");
            count.showAndWait();
            return false;
        }
        BootMenu.playerScen=pId;
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
        } catch (OverlapTilesException overlapTilesException){
            Alert overlapping = new Alert(Alert.AlertType.ERROR, overlapTilesException.getMessage() + " on Enemy Scenario\nPlease try again!");
            overlapping.setHeaderText("Overlapping Happens");
            overlapping.showAndWait();
            return false;
        } catch (OversizeException oversizeException){
            String[] error = oversizeException.getMessage().split("\\.");
            Alert oversize = new Alert(Alert.AlertType.ERROR);
            oversize.setHeaderText(error[0]);
            oversize.setContentText(error[1] + " on Enemy Scenario");
            oversize.showAndWait();
            return false;
        }

        try {
            checkGrid(fakeGrid);
        } catch (AdjacentTilesException adjacentTilesException) {
            Alert something = new Alert(Alert.AlertType.ERROR, adjacentTilesException.getMessage() + " on Enemy Scenario\nPlease try again!");
            something.setHeaderText("Adjacent Ships Error");
            something.showAndWait();
            return false;
        } catch (InvalidCountException invalidCountException){
            Alert count = new Alert(Alert.AlertType.ERROR);
            count.setHeaderText("Duplicate/Missing Ships Error");
            count.setContentText("The above error occured on Enemy Scenario.\nPlease try again!");
            count.showAndWait();
            return false;
        }
        BootMenu.enemyScen = eId;
        return true;
    }

    public void canBePlaced() throws FileNotFoundException, OversizeException {
        String path;
        path = "src/battleship/medialab/enemy_";
        if (eId == -1)
            path += "default.txt";
        else
            path += eId + ".txt";
        File PlayerDescription = new File(path);
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
                    Ship carrier = new Carrier();
                    carrier.place(x, y, or);
                    continue;
                }
                case "2": {
                    Ship battleship = new Battleship();
                    battleship.place(x, y, or);
                    continue;
                }
                case "3": {
                    Ship cruiser = new Cruiser();
                    cruiser.place(x, y, or);
                    continue;
                }
                case "4": {
                    Ship submarine = new Submarine();
                    submarine.place(x, y, or);
                    continue;
                }
                case "5": {
                    Ship destroyer = new Destroyer();
                    destroyer.place(x, y, or);
                }
            }
        }
        reader.close();
    }

    private void startGame() throws IOException {
        try {
            canBePlaced();
        } catch (OversizeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oversized Ship");
            alert.setHeaderText("Error on placing ships");
            alert.setContentText("It seems that " + e.getMessage() + "\n Please fix the problem and try again.");
            alert.showAndWait();
            return;
        }

        ((Stage) e.getScene().getWindow()).close();
        Stage main = ((Stage) ((Stage) e.getScene().getWindow()).getOwner());

        StackPane sp = new StackPane();
        GridPane game = FXMLLoader.load(getClass().getResource("GameGrid.fxml"));

        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(main.widthProperty());
        imgView.fitHeightProperty().bind(main.heightProperty());
        imgView.setOpacity(0.5);

        Region reg = new Region();
        reg.setPrefHeight(600);
        reg.setPrefWidth(800);
        reg.setDisable(true);
        reg.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        reg.setOpacity(0.0);

        sp.getChildren().addAll(imgView, game, reg);
        main.setScene(new Scene(sp, 800, 600));
        main.sizeToScene();
        main.setMinWidth(800);
        main.setMinHeight(600);
    }

    public void Validate(ActionEvent actionEvent) throws IOException {
        if(validatePlayer() && validateEnemy()) {
            startGame();
        }
    }

    public void back(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getTarget()).getScene().getWindow()).close();
    }

    public void defaults(ActionEvent actionEvent) throws IOException {
        eId = -1;
        pId = -1;
        BootMenu.enemyScen=-1;
        BootMenu.playerScen=-1;
        startGame();
    }
}
