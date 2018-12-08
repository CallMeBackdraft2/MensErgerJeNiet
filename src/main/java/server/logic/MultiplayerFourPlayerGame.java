package server.logic;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.Player;
import client.domain.enums.GameMode;
import shared.interfaces.Game;
import shared.GameLogic;

import java.util.ArrayList;
import java.util.List;


public class MultiplayerFourPlayerGame extends GameLogic implements Game {

    private ServerLobby lobby;
    private BoardStorage boardStorage;
    private List<ServerPlayer> players;
    private boolean needsUpdate;

    MultiplayerFourPlayerGame(ServerLobby serverLobby, boolean debugMode) {
        this.lobby = serverLobby;
        this.setDebugMode(debugMode);
        players = serverLobby.serverPlayers;
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
    }

    @Override
    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    @Override
    public void setNeedsUpdate(boolean bool) {
        needsUpdate  =true;
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
       if(checkPlayerTurn()) {
           super.movePawn(pawnId);
           lobby.update();
       }
    }

    @Override
    public boolean isYourTurn() {
        return getCallerId() == getCurrentPlayerId() && !isDebugMode();
    }

    @Override
    public int rollDice() throws Exception {
        if(checkPlayerTurn()) {
            int i = super.rollDice();
            lobby.update();
            return i;
        } else {
            return -1;
        }
    }

    private boolean checkPlayerTurn() throws Exception {
        if(getCallerId() != getCurrentPlayerId() && !isDebugMode()) {
            throw new Exception("Not your turn");
        }
        return true;
    }


}
