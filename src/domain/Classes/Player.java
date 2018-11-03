package domain.Classes;

import domain.Enums.PlayerColor;

public class Player {
    private int id;
    private String name;
    private PlayerColor playercolor;
    private int playerScore;
    private boolean guest;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.playercolor  = PlayerColor.values()[id];

    }

    //public setters
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setPlayercolor(PlayerColor playercolor) {
        this.playercolor = playercolor;
    }

    //public getters
    public PlayerColor getPlayercolor() {
        return this.playercolor;
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
        return id + " " +  name + " as " + playercolor;
    }
}
