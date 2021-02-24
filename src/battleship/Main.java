package battleship;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BootMenu.fxml"));
        StackPane sp = new StackPane();
        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(primaryStage.widthProperty());
        imgView.fitHeightProperty().bind(primaryStage.heightProperty());
        sp.getChildren().addAll(imgView, root);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("MediaLab Battleship");
        Scene scene = new Scene(sp, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("This is a String");
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("skata");
    }
}