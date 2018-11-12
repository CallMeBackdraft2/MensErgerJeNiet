package domain.Classes;

import domain.Enums.GameMode;

public class GameBoard {
    private GameMode gameMode;
    private PlayingField playingField;


    public GameBoard(GameMode gameMode) {
        this.gameMode = gameMode;
        playingField = new PlayingField();
    }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    public GameMode getGameMode() {
        return gameMode;
    }


}
