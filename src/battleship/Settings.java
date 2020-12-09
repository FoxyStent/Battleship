package battleship;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings {

    static String pId;
    static String eId;

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
}
