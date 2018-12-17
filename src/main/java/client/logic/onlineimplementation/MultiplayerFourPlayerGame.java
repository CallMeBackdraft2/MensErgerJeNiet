package client.logic.onlineimplementation;

import client.domain.classes.Pawn;
import client.domain.classes.Player;
import client.domain.classes.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import shared.interfaces.Game;
import client.websockets.CommunicatorWebSocket;
import com.google.gson.Gson;
import shared.Message;

import java.lang.reflect.Method;

public class MultiplayerFourPlayerGame implements Game {

    private CommunicatorWebSocket communicator;
    private boolean needsUpdate;

    public MultiplayerFourPlayerGame() {

        communicator = new CommunicatorWebSocket(this);
        Thread communicatorThread = new Thread(communicator);
        communicatorThread.run();
    }


    private Message waitForResponse(String responseName) throws Exception {
        while (true) {
            for (Message response : communicator.getResponses()) {
                if (response.getName().equals(responseName)) {

                    communicator.getResponses().remove(response);
                    return response;
                } else if (response.getName().equals("Exception")) {
                    communicator.getResponses().remove(response);
                    Gson gson = new Gson();
                    JsonElement e = gson.toJsonTree(response.getData());

                    JsonArray a = e.getAsJsonArray();
                    throw new Exception(a.get(0).getAsJsonObject().get("detailMessage").getAsString());

                }
            }
                Thread.sleep(10);

        }
    }

    private <T> T call(Class<T> tClass, Object... args) throws Exception {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String methodName = elements[2].getMethodName();
        Method method = null;
        for (Method m : this.getClass().getMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
            }
        }

        CommunicatorWebSocket.getInstance().sendMessage(new Message(methodName, args));
        if (tClass == void.class) {
            return null;
        }
        Object responseData = waitForResponse(methodName).getData()[0];
        assert method != null;
        Gson gson = new Gson();


        return gson.fromJson(gson.toJson(responseData), tClass);

    }


    @Override
    public void skipTurn() throws Exception {
        call(void.class);
    }

    @Override
    public boolean getNeedsUpdate() throws Exception {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) throws Exception {
        this.needsUpdate = needsUpdate;
    }

    @Override
    public int rollDice() throws Exception {
        return call(int.class);
    }

    @Override
    public int getDiceRolled() throws Exception {
        return call(int.class);
    }

    @Override
    public int getDiceAmountRolled() throws Exception {
        return call(int.class);
    }

    @Override
    public void movePawn(String pawnId) throws Exception {
        call(void.class, pawnId);
    }

    @Override
    public Tile[] getTiles() throws Exception {
        return call(Tile[].class);
    }

    @Override
    public Tile getPossibleMove(String pawnId) throws Exception {
        return call(Tile.class, pawnId);
    }

    @Override
    public boolean isYourTurn() throws Exception {
        return call(boolean.class);
    }

    @Override
    public Pawn getPawn(String homeTileID) throws Exception {
        return call(Pawn.class, homeTileID);
    }

    @Override
    public Pawn[] getPawns() throws Exception {
        return call(Pawn[].class);
    }

    @Override
    public int getCurrentPlayerId() throws Exception {
        return call(int.class);
    }

    @Override
    public Player[] getPlayers() throws Exception {
        return call(Player[].class);
    }

    @Override
    public void sendMessage(String message) throws Exception {
        call(void.class, message);
    }

    @Override
    public String[] getMessages() throws Exception {
        return call(String[].class);
    }

    @Override
    public boolean getIsDone() throws Exception {
        return call(boolean.class);
    }
}
