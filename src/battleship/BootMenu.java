package battleship;

import battleship.exce.OversizeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class BootMenu {
    @FXML
    private Button playButton;
    @FXML
    private Button settingsButton;

    public static int enemyScen=-1;
    public static int playerScen=-1;

    public void canBePlaced() throws FileNotFoundException, OversizeException {
        boolean res = false;
        String path;
            path = "src/battleship/medialab/enemy_";
        if (enemyScen == -1)
            path += "default.txt";
        else
            path += Integer.toString(enemyScen) + ".txt";
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

    public void playGame(ActionEvent event) throws Exception {
        System.out.println("I want to play!!!");
        if(enemyScen == -1 || playerScen == -1) {
            Alert invalidScenario = new Alert(Alert.AlertType.CONFIRMATION);
            invalidScenario.setTitle("Invalid Scenario ID");
            invalidScenario.setHeaderText("Hey, It looks like you haven't chosen valid Scenarios for both Players yet");
            invalidScenario.setContentText("Do you want to choose now?");
            ButtonType exit = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType choose = new ButtonType("Choose Scenarios");
            ButtonType cont = new ButtonType("Continue with Defaults");
            invalidScenario.getButtonTypes().setAll(cont, choose, exit);
            Optional<ButtonType> result = invalidScenario.showAndWait();
            if (result.get() == choose) {
                settingsEdit(event);
                return;
            }
            else if (result.get() == exit)
                return;

        }
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
        BorderPane game = FXMLLoader.load(getClass().getResource("GameGrid.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(game, 800, 600));
    }

    public void settingsEdit(ActionEvent actionEvent) throws Exception {
        System.out.println("I want to change my Settings");
        String res = Settings.display();
        playerScen = Integer.parseInt(res.split(",")[0]);
        enemyScen = Integer.parseInt(res.split(",")[1]);
        System.out.println(res);
    }
}
