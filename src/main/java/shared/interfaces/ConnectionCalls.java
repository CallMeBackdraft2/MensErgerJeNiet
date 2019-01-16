package shared.interfaces;

import client.domain.classes.LobbyView;
import client.domain.classes.Player;

public  interface ConnectionCalls {

    boolean login(String username, String password) throws Exception;
    boolean register(String username, String password) throws Exception;

    LobbyView[] getLobbies() throws Exception;
    boolean joinLobby(String lobbyName) throws Exception;
    boolean leaveLobby() throws Exception;
    boolean startGame() throws Exception;

    boolean openLobby() throws Exception;
    boolean setLobbyName(String newName) throws Exception;
    boolean closeLobby() throws Exception;
    String[] getInLobbyPlayers() throws Exception;
    String getLobbyName() throws Exception;
    boolean getGameStarted() throws Exception;
}
