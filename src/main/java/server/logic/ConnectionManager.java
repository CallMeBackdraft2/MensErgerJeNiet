package server.logic;

import server.rest.RestCommunicator;
import server.rest.RestResponse;
import server.websockets.MessageReceiver;
import server.websockets.Responder;
import shared.Message;
import shared.interfaces.ConnectionCalls;

import javax.websocket.Session;

public class ConnectionManager implements MessageReceiver, ConnectionCalls {

    private Responder connectionResponder;
    private RestCommunicator restCommunicator;
    public ConnectionManager() {
        connectionResponder = new Responder(this, ConnectionCalls.class);
        restCommunicator = new RestCommunicator("http://localhost:8090/");
    }

    public Responder getConnectionResponder() {
        return connectionResponder;
    }

    @Override
    public boolean login(String username, String password) throws Exception {

        checkEmpty(username, password);
        RestResponse restMessage = restCommunicator.getRequest("users", "login", username, password);
        if (!restMessage.isSucceeded()) {
            throw new Exception(restMessage.getMessage());
        }
        return true;
    }

    private boolean checkEmpty(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Username and password cannot be empty");
        }
        return true;
    }

    @Override
    public boolean register(String username, String password) throws Exception {
        checkEmpty(username, password);
         RestResponse restMessage = restCommunicator.getRequest("users", "register", username, password);
        throwIfNeeded(restMessage);
        return true;
    }

    private void throwIfNeeded(RestResponse restMessage) throws Exception {
        if (!restMessage.isSucceeded()) {
            throw new Exception(restMessage.getMessage());
        }
    }

    @Override
    public void onNewSessionConnected(Session session) {

    }

    @Override
    public void onMessageReceived(Session session, Message message) {

        if (connectionResponder.isRelevant(message)) {
            connectionResponder.callAndRespond(message, session);
        }
    }
}
