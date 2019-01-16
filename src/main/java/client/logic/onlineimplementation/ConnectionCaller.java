package client.logic.onlineimplementation;

import client.domain.classes.LobbyView;
import client.websockets.Receiver;
import shared.interfaces.ConnectionCalls;

public class ConnectionCaller implements ConnectionCalls {

    private Receiver receiver;

    public ConnectionCaller() {
        receiver = new Receiver();
    }

    @Override
    public boolean login(String username, String password) throws Exception {
        return receiver.call(boolean.class,username,password);
    }

    @Override
    public boolean register(String username, String password) throws Exception {
         return receiver.call(boolean.class,username,password);
    }

    @Override
    public LobbyView[] getLobbies() throws Exception {
        return receiver.call(LobbyView[].class);
    }

    @Override
    public boolean joinLobby(String lobbyName) throws Exception {
        return receiver.call(boolean.class, lobbyName);
    }

    @Override
    public boolean leaveLobby() throws Exception {
        return receiver.call(boolean.class);
    }

    @Override
    public boolean startGame() throws Exception {
        return receiver.call(boolean.class);
    }

    @Override
    public boolean openLobby() throws Exception {
        return receiver.call(boolean.class);
    }

    @Override
    public boolean setLobbyName(String newName) throws Exception {
        return receiver.call(boolean.class, newName);

    }

    @Override
    public boolean closeLobby() throws Exception {
        return receiver.call(boolean.class);
    }

    @Override
    public String[] getInLobbyPlayers() throws Exception {
        return receiver.call(String[].class);
    }

    @Override
    public String getLobbyName() throws Exception {
        return receiver.call(String.class);
    }

    @Override
    public boolean getGameStarted() throws Exception {
        return receiver.call(boolean.class);
    }
}
