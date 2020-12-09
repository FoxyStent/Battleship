package battleship;
import javafx.util.Pair;

public class Move {
    public int Player;
    public Pair<Integer, Integer> Bomb;
    public int PointsAwarded;
    boolean sunkShip;

    public Move(int x, int y){
        this.Bomb = new Pair<Integer, Integer>(x,y);
        sunkShip = false;
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

    public void setSunkShip(boolean sunkShip) {
        this.sunkShip = sunkShip;
    }

    public boolean isSunkShip() {
        return sunkShip;
    }

    @Override
    public String toString() {
        return "Player " + Player + " dropped a bomb at " + Bomb.getKey() + "," + Bomb.getValue() + " and got Rewarded " + PointsAwarded + " Points.";
    }
}
