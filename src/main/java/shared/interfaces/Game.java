package shared.interfaces;

import client.domain.classes.Pawn;
import client.domain.classes.Player;
import client.domain.classes.Tile;

public interface Game {

    void skipTurn() throws Exception;
    boolean getNeedsUpdate() throws Exception;
    void setNeedsUpdate(boolean bool) throws Exception;
    int rollDice() throws Exception;
    int getDiceRolled() throws Exception;
    int getDiceAmountRolled() throws Exception;
    void movePawn(String pawnId) throws Exception;
    void movePawnDebug(String pawnId, String tileId) throws Exception;
    Tile[] getTiles() throws Exception;
    Tile getPossibleMove(String pawnId) throws Exception;
    boolean isYourTurn() throws Exception;
    Pawn getPawn(String homeTileID) throws Exception;
    Pawn[] getPawns() throws Exception;
    int getCurrentPlayerId() throws Exception;
    Player[] getPlayers() throws Exception;
    void sendMessage(String message) throws Exception;
    String[] getMessages() throws Exception;
    boolean getIsDone() throws Exception;
}
