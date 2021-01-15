package battleship;

import battleship.exce.OversizeException;

import java.io.IOException;
import java.util.Random;

public class NPC extends Player{

    public NPC(boolean en, int scenario_id) throws IOException, OversizeException {
        super(en, scenario_id);
    }

    public Move makeNpcMove(Move prevMove){
        Move newMove;
        if (prevMove.hit){
            newMove = closeHit(prevMove);
        }
        else
            newMove = randomMove();

        return  newMove;
    }

    public Move closeHit(Move prevMove){
        Random rand = new Random();
        int nextX = rand.nextInt(2) - 1;
        int nextY = rand.nextInt(2) - 1;
        while (nextX == 0 && nextY == 0){
            nextX = rand.nextInt(2) - 1;
            nextY = rand.nextInt(2) - 1;
        }
        int prevX = prevMove.getX();
        int prevY = prevMove.getY();
        int X = prevX + nextX;
        int Y = prevY + nextY;
        return new Move(X, Y);
    }

    public Move randomMove(){
        Random rand = new Random();
        int X = rand.nextInt(9) + 1;
        int Y = rand.nextInt(9) + 1;
        return new Move(X,Y);
    }
}
