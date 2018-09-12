package classes;

import enums.diceNumber;
import enums.gameType;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private gameType gametype;
    private Dice dice;
    private List<Tile> tiles = new ArrayList<>();
    private int tileCount;

    public GameBoard(gameType gametype){
        this.gametype = gametype;
        if (gametype == gameType.FOURPLAYER){
            tileCount = 40;
        } else {
            tileCount = 60;
        }
        dice = new Dice();
    }

    public diceNumber rollDice(){
        return dice.rollDice();
    }

    private void generateTileList(){
        for(int i = 0; i <= tileCount; i++){
            tiles.add(new Tile());
        }
    }
}
