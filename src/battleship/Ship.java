package battleship;

import battleship.exce.OversizeException;

import java.util.Arrays;

public class  Ship{
    public int HitPoints;
    public int SinkPoints;
    public String State;
    public int size;
    public int hpLeft;
    public int StartingX;
    public int StartingY;
    public int orientation;
    public String Name;
    public boolean []Hits;

    public Ship(int HP, int SP, int s, String Nam){
        this.HitPoints = HP;
        this.SinkPoints = SP;
        this.size = s;
        Hits = new boolean[this.size];
        this.State = "Untouched";
        this.Name = Nam;
        this.hpLeft = s;
    }

    public void place(int X1, int Y1, int or) throws OversizeException {
        if (or == 1 && Y1 + this.size > 10)
            throw new OversizeException(this.Name + " is exceeding the Grid horizontally." + (X1 + this.size));
        else if (or == 2 && X1 + this.size > 10)
            throw new OversizeException(this.Name + " is exceeding the Grid");
        this.StartingX = X1;
        this.StartingY = Y1;
        this.orientation = or;
    }

    public int getHitPoints(){
        return this.HitPoints;
    }

    public int getSinkPoints(){
        return this.SinkPoints;
    }

    public int isHit(Move move){
        int ret = 0;
        int X = move.getX();
        int Y = move.getY();
        if (this.orientation == 1){
            if (this.StartingX == X){
                if (this.StartingY <= Y && this.StartingY + this.size > Y) {
                    if (!Hits[Y - StartingY]) {
                        Hits[Y - StartingY] = true;
                        this.hpLeft--;
                        this.State = "Hit";
                        move.setHit();
                        if (this.gotSunk()) {
                            ret = this.SinkPoints;
                            move.setSunkShip(true);
                        }
                        ret += this.HitPoints;
                    }
                }
            }
        }
        else if (this.orientation == 2){
            if (this.StartingY == Y){
                if (this.StartingX <= X && this.StartingX + this.size > X){
                    if(!Hits[X-StartingX]) {
                        Hits[X - StartingX] = true;
                        this.hpLeft--;
                        this.State = "Hit";
                        move.setHit();
                        if (this.gotSunk()) {
                            ret = this.SinkPoints;
                            move.setSunkShip(true);
                        }
                        ret += this.HitPoints;
                    }
                }
            }
        }
        return ret;
    }

    public boolean gotSunk(){
        for(int i=0; i<this.size; i++)
            if (!Hits[i]){
                return false;
            }
        System.out.println(Name + " got Destroyed.");
        this.State = "Sunk";

        return true;
    }

    public String getState(){
        return State;
    }

    public int getSize() {
        return size;
    }

    public int getHpLeft() {
        return hpLeft;
    }

    @Override
    public String toString() {
        return  Name + "{" +
                "HitPoints=" + HitPoints +
                ", SinkPoints=" + SinkPoints +
                ", State='" + State + '\'' +
                ", size=" + size +
                ", StartingX=" + StartingX +
                ", StartingY=" + StartingY +
                ", orientation=" + orientation +
                ", Hits=" + Arrays.toString(Hits) +
                "}\n";
    }
}

class Carrier extends Ship{

    public Carrier() {
        super(350, 1000, 5, "Carrier");

    }

}

class Battleship extends Ship{

    public Battleship() {
        super(250, 500, 4, "Battleship");
    }
}

class Cruiser extends  Ship {
    public Cruiser() {
        super(100, 250, 3, "Cruiser");
    }
}

class Submarine extends Ship{

    public Submarine() {
        super(100, 0, 3, "Submarine");
    }
}

class Destroyer extends Ship{

    public Destroyer() {
        super(50, 0, 2, "Destroyer");
    }

}