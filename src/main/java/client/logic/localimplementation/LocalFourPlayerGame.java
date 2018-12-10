package client.logic.localimplementation;

import client.domain.classes.Lobby;
import client.domain.classes.LobbyMessage;
import client.domain.classes.Player;
import shared.interfaces.Game;
import shared.GameLogic;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LocalFourPlayerGame extends GameLogic implements Game {

    Lobby lobby;

    public LocalFourPlayerGame(boolean debugMode) {
        super();

        lobby = new Lobby();
        lobby.playerJoin(new Player(0, "TestHuman"));
        lobby.playerJoin(new Player(1, "TestAI1"));
        lobby.playerJoin(new Player(2, "TestAI2"));
        lobby.playerJoin(new Player(3, "TestAI3"));
        setDebugMode(debugMode);
    }

    public LocalFourPlayerGame(Lobby lobby) {
        super();
        this.lobby = lobby;
    }

    boolean needsUpdate = false;

    @Override
    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    @Override
    public void setNeedsUpdate(boolean bool) {

        needsUpdate = bool;
    }

    @Override
    public Player[] getPlayers() {
        return lobby.getPlayers().toArray(new Player[0]);
    }

    @Override
    public void sendMessage(String message) {
        lobby.addMessage(lobby.getPlayerByIndex(0), message);
    }

    @Override
    public String[] getMessages() {

        List<String> messages = new ArrayList<>();
        for (LobbyMessage message : lobby.getMessages()) {
            messages.add(message.getPlayer().getName() + ": " + message.getMessage()
                    + " [" + message.getLocalDateTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
                    + "]");

        }
        return messages.toArray(new String[0]);
    }

    @Override
    public int rollDice() throws Exception {
        needsUpdate=true;
        return super.rollDice();
    }

    @Override
    public void movePawn(String pawnId) throws Exception {
        needsUpdate = true;
        super.movePawn(pawnId);
    }

    @Override
    public boolean isYourTurn() {
        return true;
    }
}
