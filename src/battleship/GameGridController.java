package battleship;

import battleship.*;
import battleship.exce.OverlapTilesException;
import battleship.exce.OversizeException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class GameGridController implements Initializable {
    @FXML
    private TextField X_coord;
    @FXML
    private TextField Y_coord;
    @FXML
    private GridPane rightGrid;
    @FXML
    private GridPane leftGrid;
    @FXML
    private Label yourScore;
    @FXML
    private Label enemyScore;
    @FXML
    private Label yourTurnsLabel;
    @FXML
    private Label enemyTurnsLabel;
    @FXML
    private Label yourPercLabel;
    @FXML
    private Label enemyPercLabel;
    @FXML
    private GridPane mainGrid;
    private Stage stg;

    private Player player;
    private NPC enemy;
    private int playerTurns = 1;
    private int enemyTurns = 1;
    private int yourHits = 0;
    private int enemyHits = 0;

    //TODO When an enemy hits a ship he has to search at the nearby cells. As of now, enemy searchs nearby cells only for a round. Have to Search until the ship is destroyed.
    //TODO When player plays find a cool "Wait for opponent to play" animation or else the NPC move is instantaneous and player doesnt know.

    public boolean checkEndgame() throws IOException {
        boolean ships = (player.getShipsLeft() == 0) || (enemy.getShipsLeft() == 0);
        boolean turns = (playerTurns == 39) || (enemyTurns == 39);
        if (ships || turns){
            disableGrid();
            showGameOver();
            return true;
        }
        return false;
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public void enemyMove(){
        Move enemyMove;
        enemyTurns++;
        int awardedPoints, currPoints;
        GridTile cell;

        enemyMove = enemy.makeNpcMove();
        cell = getTileFromMove(enemyMove, false);
        while(cell.isHit){
            enemy.informPredictorInvalid();
            enemyMove = enemy.makeNpcMove();
            cell = getTileFromMove(enemyMove, false);
        }

        cell.placeBomb();
        pause(300);
        awardedPoints = this.player.incomingBomb(enemyMove);
        enemy.moveResult(enemyMove);

        currPoints = Integer.parseInt(enemyScore.getText());
        enemyScore.setText(Integer.toString(currPoints+awardedPoints));
        enemyPercLabel.setText((100 * enemyHits) / enemyTurns +"%");
        enemyTurnsLabel.setText(Integer.toString(playerTurns));
        if (enemyMove.hit)
            enemyHits++;

    }

    public void dropTheBomb(ActionEvent actionEvent) throws IOException {
        if (X_coord.getText().equals("") || Y_coord.getText().equals("")){
            Alert coordAlert = new Alert(Alert.AlertType.ERROR, "Please Enter Coordinates Values", ButtonType.CLOSE);
            coordAlert.show();
            return;
        }
        int x = Integer.parseInt(X_coord.getText());
        int y = Integer.parseInt(Y_coord.getText());
        if (x <= 0 || x>10 || y <= 0 || y > 10){
            Alert coordAlert = new Alert(Alert.AlertType.ERROR, "Please Enter correct Coordinates Values", ButtonType.CLOSE);
            coordAlert.show();
            return;
        }
        GridTile cell = (GridTile) rightGrid.getChildren().get(20 + (x-1)*10 + (y-1));

        if (cell.isHit) {
            Alert droppedThere = new Alert(Alert.AlertType.WARNING, "You have already Bombed that Cell\nPlease choose another one.");
            droppedThere.showAndWait();
            return;
        }

        cell.placeBomb();
        Move move = new Move(x,y);
        player.addMove(move);
        int awardedPoints = this.enemy.incomingBomb(move);
        if (move.isHit())
            yourHits++;


        int currPoints = Integer.parseInt(yourScore.getText());
        yourScore.setText(Integer.toString(currPoints+awardedPoints));
        yourPercLabel.setText((100 * yourHits) / playerTurns +"%");
        playerTurns++;
        yourTurnsLabel.setText(Integer.toString(playerTurns));
        if(!checkEndgame()) {

            //Creating new scene and stage for the wait animation
            Parent waitParent = FXMLLoader.load(getClass().getResource("Waiting.fxml"));
            Stage waitStage = new Stage();
            waitStage.setMinHeight(300);
            waitStage.setMinWidth(300);
            waitStage.initStyle(StageStyle.UNDECORATED);
            waitStage.initModality(Modality.APPLICATION_MODAL);
            Scene aniScene = new Scene(waitParent, 300, 300);
            waitStage.setScene(aniScene);
            waitStage.show();

            //Changing modality on the main stage
            Scene mainScene = ((Node) (actionEvent.getTarget())).getScene();
            StackPane game = (StackPane) mainScene.getRoot();
            Region veil = (Region) game.getChildren().get(2);
            veil.setDisable(false);
            veil.setOpacity(0.5);
            waitStage.setOnHidden(e -> Platform.runLater(() -> {
                veil.setDisable(true);
                veil.setOpacity(0);
                enemyMove();
                try {
                    checkEndgame();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));
        }
    }

    public void startBut(ActionEvent actionEvent) throws IOException {
        playAgain();
    }

    public void loadBut(ActionEvent actionEvent) throws IOException {
        Stage set = new Stage();

        set.initModality(Modality.APPLICATION_MODAL);
        set.setTitle("Settings");
        set.setMinWidth(400);
        set.setMinHeight(300);

        StackPane sp = new StackPane();
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(set.widthProperty());
        imgView.fitHeightProperty().bind(set.heightProperty());
        sp.getChildren().addAll(imgView, root);
        set.setMinWidth(400);
        set.setMinHeight(300);
        set.setTitle("Settings");
        Scene scene = new Scene(sp, 800, 600);
        set.setScene(scene);
        set.show();
    }

    public void exitBut(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void enemyShipsBut(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EnemyShips.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        Stage popup = new Stage();
        popup.setMaxHeight(400.0);
        popup.setMaxWidth(300.0);
        popup.setScene(scene);
        EnemyShips controller = loader.getController();
        controller.setEnemy(enemy);
        popup.showAndWait();
    }

    private void showStats(Player pl) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stats.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        Stage popup = new Stage();
        popup.setMaxHeight(500.0);
        popup.setMaxWidth(400.0);
        popup.setMinHeight(500.0);
        popup.setMinWidth(400.0);
        popup.setScene(scene);
        Stats controller = loader.getController();
        controller.setStats(pl);
        popup.showAndWait();
    }

    public void playerShotsBut(ActionEvent actionEvent) {
        try {
            showStats(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enemyShotsBut(ActionEvent actionEvent) {
        try {
            showStats(enemy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showGameOver() throws IOException {
        ButtonType mainMenuBut = new ButtonType("Back to Main Menu");
        ButtonType playAgainBut = new ButtonType("Play Again!");
        Alert gameover = new Alert(Alert.AlertType.INFORMATION, "Game Over!. GG", mainMenuBut, playAgainBut);
        gameover.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> res = gameover.showAndWait();
        ButtonType resChoice = res.orElse(null);
        if (resChoice == mainMenuBut){
            mainMenu();
        }
        else if (resChoice == playAgainBut){
            playAgain();
        }
    }

    private void mainMenu() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BootMenu.fxml"));
        StackPane sp = new StackPane();
        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(stg.widthProperty());
        imgView.fitHeightProperty().bind(stg.heightProperty());
        sp.getChildren().addAll(imgView, root);
        Scene scene = new Scene(sp, 800, 600);
        stg.setScene(scene);
    }

    private void playAgain() throws IOException {
        StackPane sp = new StackPane();
        GridPane game = FXMLLoader.load(getClass().getResource("GameGrid.fxml"));

        Image img = new Image(new FileInputStream("src/battleship/assets/978648.jpg"));
        ImageView imgView= new ImageView(img);
        imgView.fitWidthProperty().bind(stg.widthProperty());
        imgView.fitHeightProperty().bind(stg.heightProperty());
        imgView.setOpacity(0.5);

        Region reg = new Region();
        reg.setPrefHeight(600);
        reg.setPrefWidth(800);
        reg.setDisable(true);
        reg.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        reg.setOpacity(0.0);

        sp.getChildren().addAll(imgView, game, reg);
        stg.setScene(new Scene(sp, 800, 600));
    }

    private void disableGrid() {
        mainGrid.setDisable(true);
        StackPane sp = (StackPane)mainGrid.getParent();
        Region reg = (Region) sp.getChildren().get(2);
        reg.setOpacity(0.7);
    }

    private GridTile getTileFromMove(Move mv, boolean enemy){
        if (enemy)
            return (GridTile) rightGrid.getChildren().get(20 + (mv.getX()-1)*10 + (mv.getY()-1));
        else
            return (GridTile) leftGrid.getChildren().get(20 + (mv.getX()-1)*10 + (mv.getY()-1));
    }

    private void addShipsOnTiles(Player pl, GridPane grid, boolean show) throws OverlapTilesException {
        Ship[] ships = pl.getPlayerShips();
        for (Ship s : ships) {
            for (int w = 0; w < s.size; w++) {
                GridTile cell;
                if (s.orientation == 1)
                    cell = (GridTile) grid.getChildren().get(20 + (s.StartingX - 1) * 10 + (s.StartingY + w - 1));

                else
                    cell = (GridTile) grid.getChildren().get(20 + (s.StartingX + w - 1) * 10 + (s.StartingY - 1));

                if (cell.isHasShip())
                    throw new OverlapTilesException("Cell " + cell.x + "," + cell.y + " has already Ship");
                else {
                    cell.setHasShip(true);
                    cell.setShip(s);
                    s.setTile(w, cell);
                    if (show) {
                        Color color = Color.ANTIQUEWHITE;
                        cell.setStyle("-fx-background-color: #" + color.toString().substring(2,8) + " ; -fx-border-color: black");
                    }
                    else{
                        cell.setOnMouseEntered(mouseEvent -> cell.setBackground(new Background(new BackgroundFill(Color.rgb(0, 100, 100, 0.7), CornerRadii.EMPTY, Insets.EMPTY))));
                        cell.setOnMouseExited(mouseEvent -> cell.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY))));
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Now Running Initializing");
        //Adding Labels
        for (int i = 1; i < 11; i++) {
            leftGrid.add(new Label(Integer.toString(i)), i, 0);
            leftGrid.add(new Label(Integer.toString(i)), 0, i);
            rightGrid.add(new Label(Integer.toString(i)), i, 0);
            rightGrid.add(new Label(Integer.toString(i)), 0, i);

        }
        //Adding Grid Tiles
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                GridTile friendlyTile = new GridTile(i, j, false);
                GridTile enemyTile = new GridTile(i, j, true);
                enemyTile.setOnMouseClicked(mouseEvent -> {
                    X_coord.setText(String.valueOf(enemyTile.x));
                    Y_coord.setText(String.valueOf(enemyTile.y));
                });
                leftGrid.add(friendlyTile, j, i);
                rightGrid.add(enemyTile, j, i);
            }
        }

        try {
            player = new Player(false, BootMenu.playerScen);
            enemy = new NPC(true, BootMenu.enemyScen);
        } catch (OversizeException | IOException e) {
            e.printStackTrace();
        }


        try {
            addShipsOnTiles(player, leftGrid, true);
            addShipsOnTiles(enemy, rightGrid, false);
        } catch (OverlapTilesException overlapTilesException) {
            overlapTilesException.printStackTrace();
        }


        Platform.runLater(() -> {
            //do something cool maybe.
            double rand = Math.random();
            if (rand > 0.5){
                Alert enemy = new Alert(Alert.AlertType.INFORMATION, "Enemy Plays");
                enemy.showAndWait();
                enemyMove();
            }
            else{
                Alert enemy = new Alert(Alert.AlertType.INFORMATION, "You are playing");
                enemy.showAndWait();
            }
            stg = (Stage) mainGrid.getScene().getWindow();
        });

    }
}
