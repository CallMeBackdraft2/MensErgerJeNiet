package domain.Classes;

import domain.Enums.GameMode;
import domain.Enums.diceNumber;

public class GameBoard {
    private GameMode gametype;
    private PlayingField playingField;


    public GameBoard(GameMode gametype) {
        this.gametype = gametype;
        playingField = new PlayingField();
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


}
