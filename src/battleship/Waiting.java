package battleship;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Waiting implements Initializable {

    @FXML
    public Arc arc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        double wait = Math.random();
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(wait*2+0.1), arc);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(360*wait+20);
        rotateTransition.setOnFinished(actionEvent -> {
            Stage stage = (Stage) arc.getScene().getWindow();
            stage.hide();
            stage.close();

        });
        rotateTransition.play();
    }

}
