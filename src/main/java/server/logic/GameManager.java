package server.logic;

import server.websockets.MessageReceiver;
import server.websockets.WebsocketServerCommunicator;
import shared.Message;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;


public class GameManager implements MessageReceiver {


    private List<Session> notInGameSession = new ArrayList<>();
    private List<ServerLobby> serverLobbies = new ArrayList<>();
    private boolean isDebugMode;
    private ConnectionManager connectionManager;

    public GameManager(boolean isDebugMode) {
        this.isDebugMode = isDebugMode;
        connectionManager = new ConnectionManager();
    }


    //Temp for websocket testing
    private void tempPutInGame() {

        ServerPlayer serverPlayer = new ServerPlayer(0, notInGameSession.get(0), "test name");
        if (serverLobbies.size() == 0) {
            serverLobbies.add(new ServerLobby(serverPlayer, isDebugMode));
        } else {
            serverLobbies.get(0).addPlayer(serverPlayer);
        }
        notInGameSession.remove(0);
    }

    @Override
    public void onNewSessionConnected(Session session) {
        notInGameSession.add(session);
        tempPutInGame();
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
        for (ServerLobby serverLobby : serverLobbies) {
            for (ServerPlayer serverPlayer : serverLobby.serverPlayers) {
                if (serverPlayer.getSession() == session) {
                    return serverPlayer;
                }
            }
        }
        return null;
    }


    @Override
    public void onMessageReceived(Session session, Message message) {

        if(connectionManager.getConnectionResponder().isRelevant(message)) {
            connectionManager.onMessageReceived(session, message);
        } else {
            ServerLobby serverLobby = findServerLobby(session);
            ServerPlayer serverPlayer = findServerPlayer(session);
            if (serverLobby != null) {
                serverLobby.newHandle(serverPlayer, message);
            }
        }
        //serverLobby.handleMessage(findServerPlayer(session), message);

    }

}
