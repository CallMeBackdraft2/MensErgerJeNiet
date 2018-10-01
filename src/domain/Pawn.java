package domain;

public class Pawn {
    private int id;
    private int pawnLoc;
    private pawnState pawnstate;

    public Pawn(int id) {
        //every newly instantiated pawn always starts in one of the four start positions of the player
        this.pawnstate = pawnState.STARTPOSITION;
        this.id = id;
    }

    public void setPawnLoc(int pawnLoc) {
        this.pawnLoc = pawnLoc;
    }

    public int getPawnLoc() {
        return this.pawnLoc;
    }

    public pawnState getPawnstate() {
        return this.pawnstate;
    }

    public void movePawnIntoPlay() {
        this.pawnstate = pawnState.INPLAY;
    }
}
