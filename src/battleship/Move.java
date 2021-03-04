package battleship;
import javafx.util.Pair;

public class Move {
    public int Player;
    public Pair<Integer, Integer> Bomb;
    public int PointsAwarded;
    boolean sunkShip;
    boolean hit;
    String shipType;

    public Move(int x, int y){
        this.Bomb = new Pair<>(x, y);
        sunkShip = false;
        hit = false;
        shipType = "Nothing";
    }

    public Pair<Integer, Integer> getBomb() {
        return this.Bomb;
    }

    public void setPointsAwarded(int pointsAwarded) {
        this.PointsAwarded = pointsAwarded;
    }

    public int getX(){
        return this.Bomb.getKey();
    }

    public int getY(){
        return this.Bomb.getValue();
    }

    public boolean isSunkShip() {
        return sunkShip;
    }

    public void setSunkShip(boolean sunkShip) {
        this.sunkShip = sunkShip;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(){
        this.hit = true;
    }

    public String getShipType() {
        return shipType;
    }

    @Override
    public String toString() {
        return "Player " + Player + " dropped a bomb at " + Bomb.getKey() + "," + Bomb.getValue() + " and got Rewarded " + PointsAwarded + " Points.";
    }
}
