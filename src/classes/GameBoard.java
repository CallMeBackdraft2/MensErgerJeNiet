package classes;

import enums.diceNumber;
import enums.gameType;

public class GameBoard {
    private gameType gametype;
    private Dice dice;
    private PlayingField playingField;
    private int playerTurnIndex;

    public GameBoard(gameType gametype) {
        this.gametype = gametype;
        this.dice = new Dice();
        playingField = new PlayingField();
    }

    public diceNumber rollDice() {
        return dice.rollDice();
    }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    public gameType getGametype() {
        return gametype;
    }

    public int getPlayerTurnIndex() {
        return playerTurnIndex;
    }

    public void switchPlayerTurn() {
        if (playerTurnIndex >= gametype.getValue()) {
            playerTurnIndex = 0;
        } else {
            playerTurnIndex++;
        }
    }
}
