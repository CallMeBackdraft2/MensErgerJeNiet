package server.websockets;

import server.MessageReceiver;
import shared.Message;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/communicator/")
public class WebsocketServerCommunicator {

    private static Map<Session, String> sessions = new LinkedHashMap<>();
    private static List<MessageReceiver> subscribers = new ArrayList<>();

    private static WebsocketServerCommunicator current;

    public static WebsocketServerCommunicator getCurrent(){

        return current;
    }

    public WebsocketServerCommunicator(){
        current = this;
    }

    @OnOpen
    public void onOpen(Session session){

        sessions.put(session,"");

    }

    public void broadCast(Message message){

    }

    @OnMessage
    public void onMessage(String messageJSON, Session session)
    {
        Message message = Message.fromJSON(messageJSON);
        System.out.println(message);
    }
}
