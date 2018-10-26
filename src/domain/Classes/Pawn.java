package domain.Classes;

import domain.Enums.PawnState;
import domain.Enums.PlayerColor;

public class Pawn {
    // Fields
    private String fullId;
    private String pawnTileId;
    private int stepsTaken;
    private PlayerColor playerColor;

    private PawnState pawnstate;

    public Pawn(String fullId) {
        this.fullId = fullId;
        playerColor = PlayerColor.fromId(fullId);
    }

    // Constructor

    // Properties
    public void setPawnTileId(String pawnTileId) {
        this.pawnTileId = pawnTileId;
    }

    public String getPawnTileId() {
        return this.pawnTileId;
    }

    public PawnState getPawnState() {
        return this.pawnstate;
    }

    public void movePawnIntoPlay() {
        this.pawnstate = PawnState.INPLAY;
    }



    public int getStepsTaken() { return stepsTaken; }

    public void setStepsTaken(int stepsTaken) { this.stepsTaken = stepsTaken; }

    public String getFullId() {
        return fullId;
    }

    public int getPlayerId(){
        return playerColor.getValue();

    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }
}
