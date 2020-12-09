package battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridTile extends Rectangle {
    public int x;
    public int y;
    private boolean hasShip;
    public boolean isHit;
    public boolean isEnemy;
    public Color color;

    public GridTile(int i, int j, boolean enemy){
        super(30,30, Color.CYAN);
        this.setStroke(Color.BLACK);
        this.x = i;
        this.y = j;
        this.isHit = false;
        this.isEnemy = enemy;
        this.color = Color.BLUE;
    }

    public void placeShips(int part, String name){

    }

    public void placeBomb(){
        this.setFill(Color.RED);
    }

    public boolean isHasShip() {
        return hasShip;
    }
}
