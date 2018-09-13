package classes;

import enums.diceNumber;
import enums.gameType;
import enums.playerColor;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private gameType gametype;
    private Dice dice;
    private List<Tile> tiles = new ArrayList<>();
    private int tileCount;
    private Player[] playerArray;
    private playerColor playerWithTurn;

    public GameBoard(gameType gametype){
        this.gametype = gametype;
        this.playerArray = new Player[4];
        if (gametype == gameType.FOURPLAYER){
            tileCount = 40;
        } else {
            tileCount = 60;
        }
        for(int i = 0; i < tileCount/10; i++){
            playerArray[i] = new Player(playerColor.values()[i]);
        }
        dice = new Dice();
    }

    public diceNumber rollDice(){
        return dice.rollDice();
    }

    public Player getPlayerByColor(playerColor color){
        Player result = null;
        for(Player p : playerArray){
            if(p.getPlayercolor() == color){
                result = p;
            }
        }
        return result;
    }

    public playerColor getPlayerWithTurn(){
        return playerWithTurn;
    }

    public void switchPlayerTurn(){
        this.playerWithTurn = playerColor.values()[playerWithTurn.getValue() == 3 ? 0 : playerWithTurn.getValue() + 1];
    }

    public void setPlayerTurn(playerColor color){
        this.playerWithTurn = color;
    }

    private void generateTileList(){
        for(int i = 0; i <= tileCount; i++){
            tiles.add(new Tile());
        }
    }
}
