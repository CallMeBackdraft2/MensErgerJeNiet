package server;

import shared.Message;

public interface MessageReceiver {

    void onMessageReceived(Message message);

}
