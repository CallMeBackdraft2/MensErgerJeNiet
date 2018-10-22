package domain.Classes;

import domain.Enums.PawnState;

public class Pawn {
    private int id;
    private int playerId;
    private int pawnTileId;
    private PawnState pawnstate;

    public Pawn(int playerId,  int id) {
        //every newly instantiated pawn always starts in one of the four start positions of the player
        this.pawnstate = PawnState.STARTPOSITION;
        this.playerId = playerId;
        this.id = id;
    }

    public void setPawnTileId(int pawnTileId) {
        this.pawnTileId = pawnTileId;
    }

    public int getPawnTileId() {
        return this.pawnTileId;
    }

    public PawnState getPawnState() {
        return this.pawnstate;
    }

    public void movePawnIntoPlay() {
        this.pawnstate = PawnState.INPLAY;
    }

    public int getPlayerId() {
        return playerId;
    }


    public int getId() {
        return id;
    }
}
