package classes;

import enums.pawnState;

public class Pawn {
    private int pawnID;
    private int pawnLoc;
    private pawnState pawnstate;

    public Pawn(int pawnID){
        //every newly instantiated pawn always starts in one of the four start positions of the player
        this.pawnstate = pawnState.STARTPOSITION;
    }

    public void setPawnLoc(int pawnLoc){
        this.pawnLoc = pawnLoc;
    }

    public int getPawnLoc() {
        return this.pawnLoc;
    }

    public pawnState getPawnstate(){
        return this.pawnstate;
    }

    public void movePawnIntoPlay() {
        this.pawnstate = pawnState.INPLAY;
    }
}
