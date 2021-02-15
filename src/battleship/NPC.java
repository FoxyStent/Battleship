package battleship;

import battleship.exce.OversizeException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class NPC extends Player{

    Move prevMove = new Move(-1, -1);
    LinkedList<Move> hits = new LinkedList<>();
    LinkedList<Move> tryNext = new LinkedList<>();

    public NPC(boolean en, int scenario_id) throws IOException, OversizeException {
        super(en, scenario_id);
    }

    public Move makeNpcMove(){
        Move newMove;
        System.out.println(tryNext.isEmpty());
        if (!tryNext.isEmpty()){
            newMove = closeHit();
        }
        else
            newMove = randomMove();

        prevMove = newMove;
        return  newMove;
    }

    public Move closeHit(){
        return tryNext.pop();
    }

    public Move randomMove(){
        Random rand = new Random();
        int X = rand.nextInt(9) + 1;
        int Y = rand.nextInt(9) + 1;
        return new Move(X,Y);
    }

    public void wasHit(Move mv){
        tryNext.add(0,new Move(mv.getX(), mv.getY()+1));
        System.out.println("added" + mv.getX() +  mv.getY()+1);
        tryNext.add(0,new Move(mv.getX()+1, mv.getY()));
        tryNext.add(0,new Move(mv.getX(), mv.getY()-1));
        tryNext.add(0,new Move(mv.getX()-1, mv.getY()));
    }

    public void sunkShip(){
        tryNext.clear();
        System.out.println("Cleared Hits");
    }
}
