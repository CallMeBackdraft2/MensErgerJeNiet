package server.logic;

import client.dal.interfaces.BoardStorage;
import client.dalfactories.DALFactory;
import client.domain.classes.Dice;
import client.domain.classes.Player;
import client.domain.enums.GameMode;
import client.logic.interfaces.Game;
import client.logic.localimplementation.GameLogic;

import java.util.ArrayList;
import java.util.List;


public class MultiplayerFourPlayerGame extends GameLogic implements Game {

    private boolean started;
    private int currentTurn;
    private ServerLobby lobby;
    private BoardStorage boardStorage;
    private Dice dice;
    private List<ServerPlayer> players;


    MultiplayerFourPlayerGame(ServerLobby serverLobby) {
        started = false;
        this.lobby = serverLobby;
        players = serverLobby.serverPlayers;
        boardStorage = DALFactory.getLocalBoardStorage();
        boardStorage.init(GameMode.FOURPLAYERBOARD);
    }

    @Override
    public boolean getNeedsUpdate() {
        return true;
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
    public void movePawn(String pawnId) {
        super.movePawn(pawnId);
        lobby.update();
    }



    @Override
    public int rollDice() {
        int i = super.rollDice();
        lobby.update();
        return i;
    }


}
