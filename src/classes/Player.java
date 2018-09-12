package classes;

import enums.pawnState;
import enums.playerColor;

public class Player {
    private playerColor playercolor;
    private Pawn[] pawnArray;
    private int PlayerScore;

    public Player(){
        pawnArray = new Pawn[4];
        for(int i = 0; i < 4; i++){
            pawnArray[i] = new Pawn(i);
            setPawnLoc(i,0);
        }
    }

    public void movePawnIntoPlay(int pawnID){
        pawnArray[pawnID].setPawnLoc(1);
        pawnArray[pawnID].movePawnIntoPlay();
    }

    public void setPawnLoc(int pawnID, int pawnLoc){
        pawnArray[pawnID].setPawnLoc(pawnLoc);
    }

    public int getPawnLoc(int pawnID){
        return pawnArray[pawnID].getPawnLoc();
    }

    public int getPlayerScore() {
        return PlayerScore;
    }

    public void setPlayerScore(int playerScore) {
        PlayerScore = playerScore;
    }

    public void setPlayercolor(playerColor playercolor) {
        this.playercolor = playercolor;
    }

    public playerColor getPlayercolor() {
        return playercolor;
    }

    public pawnState getPawnState(int pawnID) {
        return pawnArray[pawnID].getPawnstate();
    }
}
