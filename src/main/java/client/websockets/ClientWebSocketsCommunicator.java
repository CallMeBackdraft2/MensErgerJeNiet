package client.websockets;

import com.google.gson.Gson;
import shared.Message;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ClientWebSocketsCommunicator extends Communicator{

    // singleton
    private static ClientWebSocketsCommunicator instance = null;

    /*
     * The local websocket uri to connect to.
     */
    private final String uri = "ws://localhost:1234/communicator/";
    private Session session;
    private String message;
    private Gson gson = null;

    // status of the websocket client
    boolean isRunning = false;

    // Private constructor (singleton pattern)
    private ClientWebSocketsCommunicator() {
        gson = new Gson();
    }

    /*
     * Get singleton instance of this class.
     * Ensure that only one instance of this class is created.
     * @return instance of client web socket
     */
    public static ClientWebSocketsCommunicator getInstance(){
        if(instance == null){
            System.out.println("[WebSocket Client create singleton instance]");
            instance = new ClientWebSocketsCommunicator();
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
        //processMessage(message);
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
        String jsonMessage = gson.toJson(new Message("testName",5,2,"testString"));
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

    /**
    // process incoming json message
    private void processMessage(String jsonMessage){
        // parse incoming message
        WebsocketMessage message;
        try{
            message = gson.fromJson(jsonMessage, WebsocketMessage.class);
        }catch (JsonSyntaxException e){
            System.out.println("[WebSocket Client ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        // Only operation update or updateConnectedPlayers property will be further processed
        WebsocketMessageOperation operation;
        operation = message.getOperation();
        if (operation == null || operation != WebsocketMessageOperation.UPDATEPROPERTY && operation != WebsocketMessageOperation.UPDATECONNECTEDPLAYERS ) {
            System.out.println("[WebSocket Client ERROR: update property operation expected]");
            return;
        }

        // obtain property from message
        String property = message.getProperty();
        if(property == null || "".equals(property)){
            System.out.println("[WebSocket Client ERROR: property not defined]");
            return;
        }


        String content = message.getContent();
        if(content == null || "".equals(content)){
            System.out.println("[WebSocket Client ERROR: message without content]");
            return;
        }

        // create instance of communicator message for observer
        CommunicatorMessage communicatorMessage = new CommunicatorMessage();
        communicatorMessage.setProperty(property);
        communicatorMessage.setContent(content);


        // notify observers
        this.setChanged();
        this.notifyObservers(communicatorMessage);
    }
    **/

    public static void main(String[] args){
        ClientWebSocketsCommunicator communicator = new ClientWebSocketsCommunicator();
        communicator.start();
        communicator.sendMessageToServer();
    }
}
