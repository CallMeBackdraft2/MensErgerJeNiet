package domain.Classes;

import domain.Enums.GameMode;
import domain.Enums.diceNumber;

public class GameBoard {
    private GameMode gametype;
    private Dice dice;
    private PlayingField playingField;
    private int playerTurnIndex;

    public GameBoard(GameMode gametype) {
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

    public GameMode getGametype() {
        return gametype;
    }

    public int getPlayerTurnIndex() {
        return playerTurnIndex;
    }

    public void switchPlayerTurn() {
        if (playerTurnIndex >= gametype.getPlayerCount()) {
            playerTurnIndex = 0;
        } else {
            playerTurnIndex++;
        }
    }
}
