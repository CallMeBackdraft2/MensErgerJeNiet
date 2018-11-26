package client.websockets;

import client.logic.localimplementation.MultiplayerFourPlayerGame;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import shared.Message;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class CommunicatorWebSocket extends Communicator implements Runnable{

    // singleton
    private static CommunicatorWebSocket instance = null;

    /*
     * The local websocket uri to connect to.
     */
    private final String uri = "ws://localhost:1234/communicator/";
    private Session session;
    private String message;
    private Gson gson = null;
    private MultiplayerFourPlayerGame game;

    // status of the websocket client
    boolean isRunning = false;

    // Private constructor (singleton pattern)
    public CommunicatorWebSocket() {
        gson = new Gson();
    }

    public CommunicatorWebSocket(MultiplayerFourPlayerGame game) {
        gson = new Gson();
        this.game = game;
    }

    /*
     * Get singleton instance of this class.
     * Ensure that only one instance of this class is created.
     * @return instance of client web socket
     */
    public static CommunicatorWebSocket getInstance(){
        if(instance == null){
            System.out.println("[WebSocket Client create singleton instance]");
            instance = new CommunicatorWebSocket();
        }
        return instance;
    }

    /*
     * start the connection
     */
    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if(!isRunning){
            isRunning = true;
            startClient();
        }
    }

    /*
     * stop the connection
     */
    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if(isRunning){
            isRunning = false;
            stopClient();
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session){
        this.message = message;
        System.out.println("[WebSocket Client message received] " + message);
        processMessage(message);
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
        session = null;
    }


    private void sendMessageToServer(){
        String jsonMessage = gson.toJson("Henlo server");
        // use async computing
        session.getAsyncRemote().sendText(jsonMessage);
    }

    private void sendMessageToServer(String message){
        String jsonMessage = gson.toJson(message);
        // use async computing
        session.getAsyncRemote().sendText(jsonMessage);
    }


    /*
     * start a web socket client
     */
    private void startClient(){
        System.out.println("[WebSocket Client start]");
        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /*
     * stop the running client
    */
    private void stopClient(){
        System.out.println("[WebSocket Client stop]");
        try{
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // process incoming json message
    private void processMessage(String jsonMessage){
        // parse incoming message
        //WebsocketMessage message;
        Message message;
        try{
            message = gson.fromJson(jsonMessage, Message.class);
        }catch (JsonSyntaxException e){
            System.out.println("[WebSocket Client ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        switch(message.getName()){
            case "updatePawn" :
                String pawnId = (String)message.getData()[0];
                String tileId = (String)message.getData()[1];

                game.movePawn(pawnId, tileId);
                break;
            case "yourId" :
                int playerId = (int)message.getData()[0];
                break;
        }

        // notify observers
        this.setChanged();
        this.notifyObservers(message);
    }

    public static void main(String[] args){
        CommunicatorWebSocket communicator = new CommunicatorWebSocket();
        communicator.start();
        communicator.sendMessageToServer();
    }

    @Override
    public void run() {
        CommunicatorWebSocket communicator = new CommunicatorWebSocket();
        communicator.start();
    }

    public void movePawn(String pawnId, String tileId){
        sendMessageToServer(gson.toJson(new Message("movePawn", new Object[]{ pawnId, tileId })));
    }
}
