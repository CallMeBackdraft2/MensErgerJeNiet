package server.logic;

import client.domain.classes.Player;
import shared.GameLogic;
import shared.interfaces.Game;

import java.util.ArrayList;
import java.util.List;


public class MultiplayerFourPlayerGame extends GameLogic implements Game {

    private ServerLobby lobby;
    private List<ServerPlayer> players;


    MultiplayerFourPlayerGame(ServerLobby serverLobby, boolean debugMode) {
        this.lobby = serverLobby;
        this.setDebugMode(debugMode);
        players = serverLobby.serverPlayers;

    }




    @Override
    public boolean getNeedsUpdate() {
        return false;
    }

    @Override
    public void setNeedsUpdate(boolean bool) {

    }


    @Override
    public Player[] getPlayers() {
        List<Player> ps = new ArrayList<>();
        players.forEach(p -> ps.add(p.toPlayer()));
        return ps.toArray(new Player[0]);
    }

    @Override
    public void sendMessage(String message) {

        lobby.sendChatMessage(players.get(getCallerId()),message);
        lobby.update();
    }

    @Override
    public String[] getMessages() {
        return lobby.getMessages();
    }

    @Override
    public void movePawn(String pawnId) throws Exception {
        checkPlayersTurn();
           super.movePawn(pawnId);
           lobby.update();

    }

    @Override
    public boolean isYourTurn() {
        return getCallerId() == getCurrentPlayerId() && !isDebugMode();
    }

    @Override
    public int rollDice() throws Exception {
        checkPlayersTurn() ;
            int i = super.rollDice();
            lobby.update();
            return i;

    }

    private void checkPlayersTurn() throws Exception {
        if(getCallerId() != getCurrentPlayerId() && !isDebugMode()) {
            throw new Exception("Not your turn");
        }
    }


}
