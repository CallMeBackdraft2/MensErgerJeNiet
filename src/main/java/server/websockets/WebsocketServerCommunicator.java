package server.websockets;

import server.logic.ServerPlayer;
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

    private static Map<Session, ServerPlayer> sessionData = new LinkedHashMap<>();
    private static List<MessageReceiver> subscribers = new ArrayList<>();
    private static WebsocketServerCommunicator current;


    public static void subscribe(MessageReceiver receiver) {
        subscribers.add(receiver);
    }

    public static void unSubscribe(MessageReceiver receiver) {
        subscribers.remove(receiver);
    }

    public static ServerPlayer getSessionData(Session session) {

        if (!sessionData.containsKey(session)) {
            //sessionData.put(session, );
        }
        return sessionData.get(session);

    }

    @OnOpen
    public void onOpen(Session session) {

        sessionData.put(session,new ServerPlayer(0,session,"sss"));

        for (MessageReceiver subscriber : subscribers) {
            subscriber.onNewSessionConnected(session);
        }
    }

    @OnMessage
    public void onMessage(String messageJSON, Session session) {
        Message message = Message.fromJSON(messageJSON);

        for (MessageReceiver subscriber : subscribers) {
            subscriber.onMessageReceived(session, message);
        }

    }
}
