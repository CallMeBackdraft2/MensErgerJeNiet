package client.websockets;

import client.logic.onlineimplementation.MultiplayerFourPlayerGame;
import client.ui.Main;
import com.google.gson.Gson;
import shared.Message;
import shared.interfaces.Game;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class CommunicatorWebSocket extends Communicator implements Runnable {

    // singleton
    private static CommunicatorWebSocket instance = null;
    private static Session session;
    private static String message;
    private static Gson gson = null;
    private static Game game;
    private static List<Message> responses = new ArrayList<>();
    /*
     * The local websocket uri to connect to.
     */
    private final String uri = "ws://145.93.77.11:1234/communicator/";
    // status of the websocket client
    boolean isRunning = false;

    // Private constructor (singleton pattern)
    private CommunicatorWebSocket() {
        instance = this;
        gson = new Gson();
    }



    /*
     * Get singleton instance of this class.
     * Ensure that only one instance of this class is created.
     * @return instance of client web socket
     */
    public static CommunicatorWebSocket getInstance() {
        if (instance == null) {
            instance = new CommunicatorWebSocket();
            instance.start();

        }
        return instance;
    }

    public static void main(String[] args) {
        CommunicatorWebSocket communicator = new CommunicatorWebSocket();
        communicator.start();
    }

    public static void setGame(Game game) {
        CommunicatorWebSocket.game = game;
    }

    public void sendMessage(Message message) {

        if(session==null){
            responses.add(new Message("Exception", new Exception("No connection to server")));
            return;
        }
        session.getAsyncRemote().sendText(message.toJson());
    }

    public List<Message> getResponses() {
        return responses;
    }

    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            isRunning = true;
            startClient();
        }
    }

    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if (isRunning) {
            isRunning = false;
            stopClient();
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String messageText, Session session) {
        this.message = messageText;
        Message message = Message.fromJSON(messageText);
        if (message.getName().equals("update")) {
            try {
                if(game!=null)
                game.setNeedsUpdate(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        responses.add(message);
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
    }

    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(Main.arguments[0]));
        } catch (DeploymentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopClient() {
        System.out.println("[WebSocket Client stop]");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        CommunicatorWebSocket communicator = new CommunicatorWebSocket();
        communicator.start();
    }

}
