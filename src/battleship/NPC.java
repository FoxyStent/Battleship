package battleship;

import battleship.exce.OversizeException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class NPC extends Player{

    Move prevMove = new Move(-1, -1);
    LinkedList<Move> hits = new LinkedList<>();
    LinkedList<Move> tryNext = new LinkedList<>();
    private MovePredictor predictor;

    public NPC(boolean en, int scenario_id) throws IOException, OversizeException {
        super(en, scenario_id);
        predictor = new MovePredictor();
    }

    public Move makeNpcMove(){
        Move predicted = predictor.predict();
        prevMove = predicted;
        addMove(predicted);
        return  predicted;
    }

    public void moveResult(Move mv){
        predictor.processLastMove(mv);
    }

    public void informPredictorInvalid(){
        predictor.invalidMove();
    }

}
