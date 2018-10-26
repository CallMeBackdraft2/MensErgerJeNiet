package domain.Classes;

import domain.Enums.PawnState;
import domain.Enums.PlayerColor;

public class Player {
    private int id;
    private String name;
    private PlayerColor playercolor;
    private Pawn[] pawnArray;
    private int playerScore;
    private boolean guest;

    public Player(String name) {
        this.pawnArray = new Pawn[4];
        this.name = name;
        for (int i = 0; i < 4; i++) {
            //this.pawnArray[i] = new Pawn(id, i);
            setPawnLoc(i, 0);
        }
    }

    public void movePawnIntoPlay(int pawnID) {
       // pawnArray[pawnID].setPawnTileId(1);
       // pawnArray[pawnID].movePawnIntoPlay();
    }

    public void setPawnLoc(int pawnID, int pawnLoc) {
       // pawnArray[pawnID].setPawnTileId(pawnLoc);
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

    public PawnState getPawnState(int pawnID) {
        return this.pawnArray[pawnID].getPawnState();
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
}
