package classes;

import enums.pawnState;

public class Pawn {
    private int pawnID;
    private int pawnLoc;
    private pawnState pawnstate;

    public Pawn(){
        //every newly instantiated pawn always starts in one of the four start positions of the player
        this.pawnstate = pawnState.STARTPOSITION;
    }
}
