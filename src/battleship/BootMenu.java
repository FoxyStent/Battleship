package battleship;

import battleship.exce.OversizeException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class BootMenu{

    @FXML
    public VBox root;

    public static int enemyScen=-1;
    public static int playerScen=-1;

    public void canBePlaced() throws FileNotFoundException, OversizeException {
        boolean res = false;
        String path;
            path = "src/battleship/medialab/enemy_";
        if (enemyScen == -1)
            path += "default.txt";
        else
            path += enemyScen + ".txt";
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
            //noinspection OptionalGetWithoutIsPresent
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

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(window);
    }

    public void settingsEdit(ActionEvent actionEvent) throws Exception {
        Stage set = new Stage();


        set.initModality(Modality.APPLICATION_MODAL);
        set.initOwner(root.getScene().getWindow());
        set.setTitle("Settings");
        set.setMinWidth(600);
        set.setMinHeight(300);

        StackPane sp = new StackPane();
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(set.widthProperty());
        imgView.fitHeightProperty().bind(set.heightProperty());
        sp.getChildren().addAll(imgView, root);
        set.setMinWidth(600);
        set.setMinHeight(300);
        set.setTitle("Settings");
        Scene scene = new Scene(sp, 600, 300);
        set.sizeToScene();
        set.setScene(scene);
        set.show();

    }

    public void changeScene(Stage window) throws IOException {
        StackPane sp = new StackPane();
        GridPane game = FXMLLoader.load(getClass().getResource("GameGrid.fxml"));

        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(window.widthProperty());
        imgView.fitHeightProperty().bind(window.heightProperty());
        imgView.setOpacity(0.5);

        Region reg = new Region();
        reg.setPrefHeight(600);
        reg.setPrefWidth(800);
        reg.setDisable(true);
        reg.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        reg.setOpacity(0.0);

        sp.getChildren().addAll(imgView, game, reg);
        window.setScene(new Scene(sp, 800, 600));
        window.sizeToScene();
        window.setMinWidth(800);
        window.setMinHeight(600);

    }

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
