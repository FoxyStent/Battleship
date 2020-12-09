package battleship;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BootMenu.fxml"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("GEIA SOU FILEEE");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        System.out.println("This is a String");
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("skata");
    }
}