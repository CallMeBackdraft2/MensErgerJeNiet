package server.logic;

import server.MessageReceiver;
import shared.Message;

import javax.websocket.Session;
import java.util.*;

public class GameManager implements MessageReceiver {


    private List<ServerPlayer> notInGameSession = new ArrayList<>();
    private List<ServerLobby> serverLobbies = new ArrayList<>();

    public GameManager() {


    }


    //Temp for websocket testing
    private void tempPutInGame(){

        ServerPlayer serverPlayer = notInGameSession.get(0);
        if(serverLobbies.size()==0){
            serverLobbies.add(new ServerLobby(serverPlayer));
        } else {
            serverLobbies.get(0).addPlayer(serverPlayer);
        }
        notInGameSession.remove(serverPlayer);
    }

    @Override
    public void onNewSessionConnected(Session session) {

        notInGameSession.add(new ServerPlayer(0, session,"testName"));
        tempPutInGame();
    }

    private ServerLobby findServerLobby(Session session){
        for (ServerLobby serverLobby : serverLobbies) {
            for (ServerPlayer serverPlayer : serverLobby.serverPlayers) {
                if(serverPlayer.getSession() == session){
                    return serverLobby;
                }
            }
        }
        return null;
    }


    private ServerPlayer findServerPlayer(Session session){
        for (ServerLobby serverLobby : serverLobbies) {
            for (ServerPlayer serverPlayer : serverLobby.serverPlayers) {
                if(serverPlayer.getSession() == session){
                    return serverPlayer;
                }
            }
        }
        return null;
    }

    @Override
    public void onMessageReceived(Session session, Message message) {

         Objects.requireNonNull(findServerLobby(session)).handleMessage(findServerPlayer(session),message);

    }
}
