package server.logic;

import client.domain.classes.LobbyView;
import server.rest.RestCommunicator;
import server.rest.RestResponse;
import server.websockets.MessageReceiver;
import server.websockets.Responder;
import shared.Message;
import shared.interfaces.ConnectionCalls;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;


public class GameManager implements MessageReceiver, ConnectionCalls {


    private static GameManager instance;
    private RestCommunicator restCommunicator;
    private List<ServerLobby> serverLobbies = new ArrayList<>();
    private List<ServerPlayer> allPlayers = new ArrayList<>();
    private Responder responder;


    private GameManager() {
        restCommunicator = new RestCommunicator("http://localhost:8090/");
        responder = new Responder(this, ConnectionCalls.class);


    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public List<ServerLobby> getServerLobbies() {
        return serverLobbies;
    }



    @Override
    public void onNewSessionConnected(Session session) {
        allPlayers.add(new ServerPlayer(session));
       // tempPutInGame();
    }

    private ServerLobby findServerLobby(Session session) {
        for (ServerLobby serverLobby : serverLobbies) {
            for (ServerPlayer serverPlayer : serverLobby.serverPlayers) {
                if (serverPlayer.getSession() == session) {
                    return serverLobby;
                }
            }
        }
        return null;
    }

    private ServerPlayer findServerPlayer(Session session) {
        for (ServerPlayer serverPlayer : allPlayers) {
            if(serverPlayer.getSession() == session){
                return serverPlayer;
            }
        }
        return null;
    }

    @Override
    public void onMessageReceived(Session session, Message message) {

        if (responder.isRelevant(message)) {
            responder.callAndRespond(message, session);
        } else {
            ServerLobby serverLobby = findServerLobby(session);
            ServerPlayer serverPlayer = findServerPlayer(session);
            if (serverLobby != null) {
                serverLobby.newHandle(serverPlayer, message);
            }
        }
        //serverLobby.handleMessage(findServerPlayer(session), message);

    }

    @Override
    public boolean login(String username, String password) throws Exception {

        checkEmpty(username, password);
        RestResponse restMessage = restCommunicator.getRequest("users", "login", username, password);
        if (!restMessage.isSucceeded()) {
            throw new Exception(restMessage.getMessage());
        }
        findServerPlayer(responder.getCaller()).setUsername(username);
        return true;
    }

    private boolean checkEmpty(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Username and password cannot be empty");
        }
        return true;
    }

    @Override
    public boolean register(String username, String password) throws Exception {
        checkEmpty(username, password);
        RestResponse restMessage = restCommunicator.getRequest("users", "register", username, password);
        throwIfNeeded(restMessage);
        return true;
    }

    @Override
    public LobbyView[] getLobbies() throws Exception {
        List<LobbyView> lobbyViews = new ArrayList<>();
        for (ServerLobby serverLobby : GameManager.getInstance().getServerLobbies()) {
            lobbyViews.add(serverLobby.toLobbyView());
        }
        return lobbyViews.toArray(new LobbyView[0]);
    }

    @Override
    public boolean joinLobby(String lobbyName) throws Exception {
        leaveLobby();
        ServerLobby wanted = null;
        for (ServerLobby serverLobby : serverLobbies) {
            if (serverLobby.getName().equals(lobbyName)) {
                wanted = serverLobby;
            }
        }
        if (wanted == null) {
            throw new Exception("Can't find lobby with name " + lobbyName);
        }
        wanted.addPlayer(findServerPlayer(responder.getCaller()));
        wanted.update();
        return true;
    }

    @Override
    public boolean leaveLobby() throws Exception {
        ServerLobby current = findServerLobby(responder.getCaller());
        if (current == null) {
            return false;
        }
        current.serverPlayers.remove(findServerPlayer(responder.getCaller()));
        return true;
    }

    @Override
    public boolean startGame() throws Exception {
        checkInLobby();
        checkLobbyOwned();

        ServerLobby serverLobby = findServerLobby(responder.getCaller());
        if(serverLobby.serverPlayers.size() !=4) {
            throw new Exception("You need 4 players to start a game");
        }
        serverLobby.setStarted(true);
        return true;
    }

    @Override
    public boolean openLobby() throws Exception {
        leaveLobby();
        ServerPlayer serverPlayer = findServerPlayer(responder.getCaller());
        ServerLobby serverLobby = new ServerLobby(serverPlayer, false);
        serverLobby.setName(serverPlayer.getUsername() + "'s lobby");
        serverLobbies.add(serverLobby);
        return true;
    }

    private void checkLobbyOwned() throws Exception {
        if (responder.getCaller() != findServerLobby(responder.getCaller()).getHost().getSession()) {
            throw new Exception("Only host can change settings");
        }
    }

    @Override
    public boolean setLobbyName(String newName) throws Exception {
        checkLobbyOwned();
        findServerLobby(responder.getCaller()).setName(newName);
        return false;
    }

    @Override
    public boolean closeLobby() throws Exception {
        ServerLobby lobby = findServerLobby(responder.getCaller());
        assert lobby != null;
        lobby.update();
        serverLobbies.remove(lobby);
        return true;
    }

    @Override
    public String[] getInLobbyPlayers() throws Exception {


        checkInLobby();
        List<String> playerNames = new ArrayList<>();
        for (ServerPlayer serverPlayer : findServerLobby(responder.getCaller()).serverPlayers) {
            playerNames.add(serverPlayer.getUsername());
        }
        return playerNames.toArray(new String[0]);
    }

    private void checkInLobby() throws Exception {
        if (findServerLobby(responder.getCaller() )== null) {
            throw new Exception("You are not in a lobby.");
        }
    }

    @Override
    public String getLobbyName() throws Exception {
        checkInLobby();
        ServerLobby serverLobby =  findServerLobby(responder.getCaller());
        if(serverLobby.getName() == null){
            throw new Exception("Empty string");
        }
        return serverLobby.getName();
    }

    @Override
    public boolean getGameStarted() throws Exception {
        checkInLobby();
        ServerLobby serverLobby =  findServerLobby(responder.getCaller());
        return serverLobby.getStarted();
    }

    private void throwIfNeeded(RestResponse restMessage) throws Exception {
        if (!restMessage.isSucceeded()) {
            throw new Exception(restMessage.getMessage());
        }
    }
}
