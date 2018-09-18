package classes;

import enums.pawnState;
import enums.playerColor;

public class Player {
    private int id;
    private String name;
    private playerColor playercolor;
    private Pawn[] pawnArray;
    private int playerScore;
    private boolean guest;

    public Player(String name) {
        this.pawnArray = new Pawn[4];
        this.name = name;
        for (int i = 0; i < 4; i++) {
            this.pawnArray[i] = new Pawn(i);
            setPawnLoc(i, 0);
        }
    }

    public void movePawnIntoPlay(int pawnID) {
        pawnArray[pawnID].setPawnLoc(1);
        pawnArray[pawnID].movePawnIntoPlay();
    }

    public void setPawnLoc(int pawnID, int pawnLoc) {
        pawnArray[pawnID].setPawnLoc(pawnLoc);
    }

    //public setters
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setPlayercolor(playerColor playercolor) {
        this.playercolor = playercolor;
    }

    //public getters
    public playerColor getPlayercolor() {
        return this.playercolor;
    }

    public pawnState getPawnState(int pawnID) {
        return this.pawnArray[pawnID].getPawnstate();
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public int getPawnLoc(int pawnID) {
        return this.pawnArray[pawnID].getPawnLoc();
    }

    public String getName() {
        return this.name;
    }
}
