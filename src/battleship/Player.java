package battleship;

import battleship.exce.OversizeException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Player {
    public int score;
    public int shipsLeft;
    private final int scenarioID;
    private final boolean isEnemy;
    public Ship[] playerShips;

    public Player(boolean en, int scenario_id) throws IOException, OversizeException {
        this.scenarioID = scenario_id;
        this.isEnemy = en;
        this.shipsLeft = 5;
        this.score = 0;
        this.playerShips = new Ship[5];
        getAndPlaceShips();
    }

    public void getAndPlaceShips() throws IOException, OversizeException {
        String path;
        if (isEnemy)
            path = "src/battleship/medialab/enemy_";
        else
            path = "src/battleship/medialab/player_";
        if (scenarioID == -1)
            path += "default.txt";
        else
            path += scenarioID + ".txt";

        File PlayerDescription = new File(path);
        Scanner reader = new Scanner(PlayerDescription);
        String[] tokens;
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            tokens = data.split(",");
            int x = Integer.parseInt(tokens[1]) + 1;
            int y = Integer.parseInt(tokens[2]) + 1;
            int or = Integer.parseInt(tokens[3]);
            switch (tokens[0]) {
                case "1": {
                    Ship carrier = new Carrier();
                    carrier.place(x, y, or);
                    this.playerShips[Integer.parseInt(tokens[0]) - 1] = carrier;
                    continue;
                }
                case "2": {
                    Ship battleship = new Battleship();
                    battleship.place(x, y, or);
                    this.playerShips[Integer.parseInt(tokens[0]) - 1] = battleship;
                    continue;
                }
                case "3": {
                    Ship cruiser = new Cruiser();
                    cruiser.place(x, y, or);
                    this.playerShips[Integer.parseInt(tokens[0]) - 1] = cruiser;
                    continue;
                }
                case "4": {
                    Ship submarine = new Submarine();
                    submarine.place(x, y, or);
                    this.playerShips[Integer.parseInt(tokens[0]) - 1] = submarine;
                    continue;
                }
                case "5": {
                    Ship destroyer = new Destroyer();
                    destroyer.place(x, y, or);
                    this.playerShips[Integer.parseInt(tokens[0]) - 1] = destroyer;
                }
            }
        }
        reader.close();
    }

    public void setScore(int score) {
        this.score = this.score + score;
    }

    public int getScore() {
        return this.score;
    }

    //public Ship getPlayerShips(Type type) {
        //return playerShips[type.ordinal()];
    //}

    public Move dropABomb(int X, int Y){
        return new Move(X,Y);
    }

    public int incomingBomb(Move move){
        int hit = 0;
        for (int i=0; i<5; i++)
            hit += playerShips[i].isHit(move);
        move.setPointsAwarded(hit);
        if (move.isSunkShip())
            shipsLeft--;
        return hit;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    @Override
    public String toString() {
        return "Player{\n" +
                "Score=" + score +
                ", isEnemy=" + isEnemy +
                ", PlayerShips=\n" + Arrays.toString(playerShips) +
                '}';
    }
}
