package server;

import shared.Message;

import javax.websocket.Session;

public interface MessageReceiver {

    void onNewSessionConnected(Session session);
    void onMessageReceived(Session session, Message message);

}
