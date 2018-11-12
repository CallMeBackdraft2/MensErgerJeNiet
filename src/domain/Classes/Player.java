package domain.Classes;

import domain.Enums.PlayerColor;

public class Player {
    private int id;
    private String name;
    private PlayerColor playerColor;
    private int playerScore;
    private boolean guest;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.playerColor = PlayerColor.values()[id];

    }

    //public setters
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    //public getters
    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }


    public int getPlayerScore() {
        return this.playerScore;
    }

   /* public int getPawnLoc(int pawnID) {
        return this.pawnArray[pawnID].getPawnTileId();
    }*/

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return id + " " +  name + " as " + playerColor;
    }
}
