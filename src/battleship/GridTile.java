package battleship;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class GridTile extends StackPane {
    public int x;
    public int y;
    private boolean hasShip;
    public boolean isHit;
    public boolean isEnemy;
    private Ship ship;

    public GridTile(int i, int j, boolean enemy){
        super();
        super.setMinSize(30.0, 30.0);
        setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        if (j==10)
            if (i==10)
                setStyle("-fx-border-width: 1px 1px 1px 1px; -fx-border-color:black black black black");
            else
                setStyle("-fx-border-width: 1px 1px 0px 1px; -fx-border-color:black black black black");
        else if (i==10)
            setStyle("-fx-border-width: 1px 0px 1px 1px; -fx-border-color:black black black black");
        else
            setStyle("-fx-border-width: 1px 0px 0px 1px; -fx-border-color:black black black black");
        if (enemy) {
            setOnMouseEntered(mouseEvent -> setBackground(new Background(new BackgroundFill(Color.rgb(0, 100, 100, 0.7), CornerRadii.EMPTY, Insets.EMPTY))));
            setOnMouseExited(mouseEvent -> setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY))));
        }
        this.x = i;
        this.y = j;
        this.isHit = false;
        this.isEnemy = enemy;
        this.ship = null;
    }

    public void setHasShip(boolean s){
        this.hasShip = s;
        this.setOnMouseEntered(mouseEvent -> {});
        this.setOnMouseExited(mouseEvent -> {});
    }

    public void placeBomb(){
        isHit = true;
        Circle bomb = new Circle(10);
        bomb.setFill(Paint.valueOf("fff"));
        bomb.setStroke(Paint.valueOf("000"));
        this.getChildren().add(bomb);
    }

    public void hitShip(){
        ((Circle)(this.getChildren().get(0))).setFill(Paint.valueOf("ff2626"));
    }

    public void sunkShip(){
        ((Circle)(this.getChildren().get(0))).setFill(Paint.valueOf("8a0000"));
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isHasShip() {
        return hasShip;
    }

    @Override
    public String toString() {
        return "GridTile{" +
                ", Exited=" + this.getOnMouseExited() +
                ", Entered=" + this.getOnMouseEntered() +
                '}';
    }
}


