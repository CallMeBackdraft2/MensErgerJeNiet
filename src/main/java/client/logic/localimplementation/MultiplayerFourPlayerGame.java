package client.logic.localimplementation;

import client.domain.classes.*;
import client.logic.interfaces.Game;
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



    public Message waitForResponse(String responseName) {
        while (true) {
            for (Message response : communicator.getResponses()) {
                if (response.getName().equals(responseName)) {

                    communicator.getResponses().remove(response);
                    return response;
                } else if(response.getName().equals("Exception")){
                    System.out.println(response.getData()[0]);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> T call(Class<T> tClass, Object... args) {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String methodName = elements[2].getMethodName();
        Method method = null;
        for (Method m : this.getClass().getMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
            }
        }

        CommunicatorWebSocket.getInstance().sendMessage(new Message(methodName, args));
        if(tClass == void.class){
            return null;
        }
        Object responseData = waitForResponse(methodName).getData()[0];
        assert method != null;
        Gson gson = new Gson();


        return gson.fromJson(gson.toJson(responseData), tClass);

    }



    @Override
    public void skipTurn() {
        call(void.class);
    }

    @Override
    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    @Override
    public int rollDice() {
        return call(int.class);
    }

    @Override
    public int getDiceRolled() {
       return call(int.class);
    }

    @Override
    public int getDiceAmountRolled() {
        return call(int.class);
    }

    @Override
    public void movePawn(String pawnId) {
        call(void.class, pawnId);
    }

    @Override
    public Tile[] getTiles() {
        return call(Tile[].class);
    }

    @Override
    public Tile getPossibleMove(String pawnId) {
       return call(Tile.class,pawnId);
    }

    @Override
    public boolean isYourTurn() {
       return true;
    }

    @Override
    public Pawn getPawn(String homeTileID) {
      return   call(Pawn.class,homeTileID);
    }

    @Override
    public Pawn[] getPawns() {
        return call(Pawn[].class);
    }

    @Override
    public int getCurrentPlayerId() {
       return call(int.class);
    }

    @Override
    public Player[] getPlayers() {
       return call(Player[].class);
    }

    @Override
    public void sendMessage(String message) {
            call(void.class,message);
    }

    @Override
    public String[] getMessages() {
        return call(String[].class);
    }

    @Override
    public boolean getIsDone() {
        return false;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
}
