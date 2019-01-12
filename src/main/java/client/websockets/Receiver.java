package client.websockets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import shared.Message;

import java.lang.reflect.Method;

public class Receiver {


    private CommunicatorWebSocket communicator;

    public Receiver() {
        communicator = CommunicatorWebSocket.getInstance();
    }

    public <T> T call(Class<T> tClass, Object... args) throws Exception {

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
}
