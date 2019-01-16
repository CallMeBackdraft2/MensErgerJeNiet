package client.logic.onlineimplementation;

import client.domain.classes.Pawn;
import client.domain.classes.Player;
import client.domain.classes.Tile;
import client.websockets.CommunicatorWebSocket;
import client.websockets.Receiver;
import shared.interfaces.Game;

public class MultiplayerFourPlayerGame implements Game {

    private boolean needsUpdate;
    private Receiver receiver;

    public MultiplayerFourPlayerGame() {

        CommunicatorWebSocket.setGame(this);
        receiver = new Receiver();

    }



    @Override
    public void skipTurn() throws Exception {
        receiver.call(void.class);
    }

    @Override
    public boolean getNeedsUpdate() throws Exception {
        return CommunicatorWebSocket.isNeedsUpdate();
    }

    public void setNeedsUpdate(boolean needsUpdate) throws Exception {
        CommunicatorWebSocket.setNeedsUpdate(needsUpdate);
    }

    @Override
    public int rollDice() throws Exception {

        return receiver.call(int.class);
    }

    @Override
    public int getDiceRolled() throws Exception {
        return receiver.call(int.class);
    }

    @Override
    public int getDiceAmountRolled() throws Exception {
        return receiver.call(int.class);
    }

    @Override
    public void movePawn(String pawnId) throws Exception {
        receiver.call(void.class, pawnId);
    }

    @Override
    public void movePawnDebug(String pawnId, String tileId) throws Exception {
        receiver.call(void.class, pawnId,tileId);
    }

    @Override
    public Tile[] getTiles() throws Exception {
        return receiver.call(Tile[].class);
    }

    @Override
    public Tile getPossibleMove(String pawnId) throws Exception {
        return receiver.call(Tile.class, pawnId);
    }

    @Override
    public boolean isYourTurn() throws Exception {
        return receiver.call(boolean.class);
    }

    @Override
    public Pawn getPawn(String homeTileID) throws Exception {
        return receiver.call(Pawn.class, homeTileID);
    }

    @Override
    public Pawn[] getPawns() throws Exception {
        return receiver.call(Pawn[].class);
    }

    @Override
    public int getCurrentPlayerId() throws Exception {
        return receiver.call(int.class);
    }

    @Override
    public Player[] getPlayers() throws Exception {
        return receiver.call(Player[].class);
    }

    @Override
    public void sendMessage(String message) throws Exception {
        receiver.call(void.class, message);
    }

    @Override
    public String[] getMessages() throws Exception {
        return receiver.call(String[].class);
    }

    @Override
    public boolean getIsDone() throws Exception {
        return receiver.call(boolean.class);
    }
}
