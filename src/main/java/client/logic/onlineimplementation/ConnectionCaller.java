package client.logic.onlineimplementation;

import client.websockets.Receiver;
import shared.interfaces.ConnectionCalls;

public class ConnectionCaller implements ConnectionCalls {

    Receiver receiver;

    public ConnectionCaller() {
        receiver = new Receiver();
    }

    @Override
    public boolean login(String username, String password) throws Exception {
        return receiver.call(boolean.class,username,password);
    }

    @Override
    public boolean register(String username, String password) throws Exception {
         return receiver.call(boolean.class,username,password);
    }
}
