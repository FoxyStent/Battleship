package battleship;


import javafx.util.Pair;

import java.util.*;

public class MovePredictor {
    private Move lastHit;
    private Move firstHit;
    private String lastPredicted;
    private String orientation;
    private int hitCounts;
    LinkedList<Pair<Move, String>> predictions;
    Map<String, Boolean> validDirections;
    Set<Integer> hits;

    public MovePredictor(){
        hitCounts = 0;
        lastHit = null;
        firstHit = null;
        orientation = "None";
        lastPredicted = "None";
        hits = new HashSet<>();
        predictions = new LinkedList<>();
        validDirections = new HashMap<>();
        validDirections.put("D", true);
        validDirections.put("U", true);
        validDirections.put("R", true);
        validDirections.put("L", true);
    }

    public void processLastMove(Move mv){
        if (mv.hit) {
            hits.add(mv.getX()*10 + mv.getY());
            if (firstHit == null)
                addFirstHit(mv);
            else{
                if (lastHit == null)
                    updateOrientation(mv);
                addLastHit(mv);
            }
        }
        else{
            if (!predictions.isEmpty()){
                validDirections.put(lastPredicted, false);
                predictions.removeIf(pair -> pair.getValue().equals(lastPredicted));
            }
        }
        if (mv.isSunkShip()) {
            clearPredictor();
        }

    }

    public Move predict(){
        if (!predictions.isEmpty()) {
            lastPredicted = predictions.getFirst().getValue();
            return predictions.pop().getKey();
        }
        else
            return randomMove();
    }

    public void invalidMove(){
        if (!predictions.isEmpty())
            validDirections.put(lastPredicted, false);
    }

    private Move randomMove(){
        Random rand = new Random();
        int above, below, left, right;
        int X,Y;
        do{
            X = rand.nextInt(9) + 1;
            Y = rand.nextInt(9) + 1;
            above = (X - 1) * 10 + Y;
            below = (X + 1) * 10 + Y;
            left = X * 10 + Y - 1;
            right = X * 10 + Y + 1;
        }while (hits.contains(above) || hits.contains(below) || hits.contains(left) || hits.contains(right));
        return new Move(X, Y);
    }

    private void addFirstHit(Move mv){
        firstHit = mv;
        hitCounts++;
        addAllAdjacent(mv);
    }

    private void addLastHit(Move mv){
        lastHit = mv;
        hitCounts++;
        updatePredictions();
    }

    private void clearPredictor(){
        firstHit = null;
        lastHit = null;
        hitCounts = 0;
        orientation = "None";
        predictions.clear();
        validDirections.put("D", true);
        validDirections.put("U", true);
        validDirections.put("R", true);
        validDirections.put("L", true);
    }

    private void updateOrientation(Move mv){
        if (firstHit != null) {
            int dX = firstHit.getX() - mv.getX();
            int dY = firstHit.getY() - mv.getY();
            if (dX != 0) {
                orientation = "L/R";
                predictions.removeIf(pair -> pair.getValue().equals("U"));
                predictions.removeIf(pair -> pair.getValue().equals("D"));
            }
            else if (dY != 0) {
                orientation = "D/U";
                predictions.removeIf(pair -> pair.getValue().equals("L"));
                predictions.removeIf(pair -> pair.getValue().equals("R"));
            }
        }
    }

    private void addAllAdjacent(Move mv){
        if (firstHit.getY() + 1 < 11)
            predictions.add(new Pair<>(new Move(firstHit.getX(), firstHit.getY()+1), "D"));
        if (firstHit.getX() + 1 < 11)
            predictions.add(new Pair<>(new Move(firstHit.getX()+1, firstHit.getY()), "R"));
        if (firstHit.getY() - 1 > 0)
            predictions.add(new Pair<>(new Move(firstHit.getX()-1, firstHit.getY()), "L"));
        if (firstHit.getY() - 1 > 0)
            predictions.add(new Pair<>(new Move(firstHit.getX(), firstHit.getY()-1), "U"));
    }

    private void updatePredictions(){
        if(orientation.equals("L/R")){
            if (validDirections.get("R"))
                predictions.add(new Pair<>(new Move(firstHit.getX()+hitCounts, firstHit.getY()), "R"));
            if (validDirections.get("L"))
                predictions.add(new Pair<>(new Move(firstHit.getX()-hitCounts, firstHit.getY()), "L"));
        }
        else if(orientation.equals("D/U")){
            if (validDirections.get("D"))
                predictions.add(new Pair<>(new Move(firstHit.getX(), firstHit.getY()+hitCounts), "D"));
            if (validDirections.get("U"))
                predictions.add(new Pair<>(new Move(firstHit.getX(), firstHit.getY()-hitCounts), "U"));
        }
    }
}
