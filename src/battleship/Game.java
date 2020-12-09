package battleship;

import java.io.*;
import java.util.Scanner;

//TODO Do not allow same move for a player
//TODO Turn some classes to private.
//TODO Add Custom exceptions
//TODO Maybe make the Drop Bomb Button be the turn handler. If so; the handler will make the players move and then call PC's move function.
//TODO https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
//TODO https://code.tutsplus.com/tutorials/understanding-the-game-loop-basix--active-8510?_ga=2.156894706.898538387.1605129979-2116969898.1605129979
//TODO

public class Game {

    public static void main(String[] args) throws IOException {
        /*
        //Player player = new Player(1);
        //Player enemy = new Player(2);
        int[] shipsLeft = {5, 5};//How many Ships left has each player.
        int[] score = {0,0};
        int[] playersTurns = new int[2];//How many rounds has each player played.

        //Reading Files, Creating Ships.
        try {
            File PlayerDescription = new File("C:\\medialab_project\\src\\player_default.txt");
            File EnemyDescription = new File("C:\\medialab_project\\src\\enemy_default.txt");
            Scanner reader = new Scanner(PlayerDescription);
            String[] tokens;
            while (reader.hasNextLine()){
                String data = reader.nextLine();
                tokens = data.split(",");
                switch (tokens[0]) {
                    case "1" :  {
                        Ship carrier = new Carrier();
                        carrier.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        //player.setPlayerShips(carrier, Player.Type.CARRIER);
                        continue;
                    }
                    case "2":{
                        Ship battleship = new Battleship();
                        battleship.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        player.setPlayerShips(battleship, Player.Type.BATTLESHIP);
                        continue;
                    }
                    case "3":{
                        Ship cruiser = new Cruiser();
                        cruiser.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        player.setPlayerShips(cruiser, Player.Type.CRUISER);
                        continue;
                    }
                    case "4":{
                        Ship submarine = new Submarine();
                        submarine.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        player.setPlayerShips(submarine, Player.Type.SUBMARINE);
                        continue;
                    }
                    case "5":{
                        Ship destroyer = new Destroyer();
                        destroyer.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        player.setPlayerShips(destroyer, Player.Type.DESTROYER);
                    }
                }
            }
            reader.close();
            reader = new Scanner(EnemyDescription);
            while (reader.hasNextLine()){
                String data = reader.nextLine();
                tokens = data.split(",");
                switch (tokens[0]) {
                    case "1" :  {
                        Ship carrier = new Carrier();
                        carrier.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        enemy.setPlayerShips(carrier, Player.Type.CARRIER);
                        continue;
                    }
                    case "2":{
                        Ship battleship = new Battleship();
                        battleship.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        enemy.setPlayerShips(battleship, Player.Type.BATTLESHIP);
                        continue;
                    }
                    case "3":{
                        Ship cruiser = new Cruiser();
                        cruiser.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        enemy.setPlayerShips(cruiser, Player.Type.CRUISER);
                        continue;
                    }
                    case "4":{
                        Ship submarine = new Submarine();
                        submarine.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        enemy.setPlayerShips(submarine, Player.Type.SUBMARINE);
                        continue;
                    }
                    case "5":{
                        Ship destroyer = new Destroyer();
                        destroyer.place(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                        enemy.setPlayerShips(destroyer, Player.Type.DESTROYER);
                    }
                }
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("File not Found");
            e.printStackTrace();
        }

        System.out.println("Created Player: " + player.toString());
        System.out.println("Created Enemy: " + enemy.toString());
        int turn=0;
        while(turn != 40 && shipsLeft[0] != 0 && shipsLeft[1] != 0) {
            System.out.println("Please Enter Coordinates to Drop a Bomb");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] coords = reader.readLine().split(",");
            Move one = player.dropABomb(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            score[0] += enemy.incomingBomb(one);
            shipsLeft[0] = player.getShipsLeft();
            shipsLeft[1] = enemy.getShipsLeft();
            System.out.println(one);
            turn++;
        }

        System.out.println("Game Ended with Score: Player -> " + score[0] + ", PC -> " + score[1]);

         */
    }


}
