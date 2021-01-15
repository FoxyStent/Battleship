package battleship;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Waiting implements Initializable {

    @FXML
    public Arc arc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), arc);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(720.0);
        rotateTransition.setOnFinished(actionEvent -> arc.getScene().getWindow().hide());
        rotateTransition.play();
    }

}
