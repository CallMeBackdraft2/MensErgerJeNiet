package server.websockets;

import com.google.gson.Gson;
import shared.Message;

import javax.websocket.Session;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Responder {

    private Object target;
    private Class interfaceType;
    private Method[] methods;
    private Session caller;

    public Responder(Object target, Class interfaceType) {
        this.target = target;
        this.interfaceType = interfaceType;
        methods = interfaceType.getMethods();
    }

    public boolean isRelevant(Message message) {
        return getMethod(message.getName()) != null;
    }

    public void callAndRespond(Message message, Session session) {

        caller = session;
        if (message.getName().equals("addLine")) {
            int c = 4;
        }
        Method method = getMethod(message.getName());
        if (method == null) {
            throw new IllegalArgumentException("Method not found");
        }
        Object result = null;

        try {
            result = call(method, cleanData(method, message.getData()));
            if (method.getReturnType() != void.class) {
                Gson gson = new Gson();
                result = gson.fromJson(gson.toJson(result), method.getReturnType());
                respond(new Message(message.getName(), result), session);
            }
        } catch (InvocationTargetException e) {
            respond(new Message("Exception", e.getTargetException()), session);
        } catch (IllegalAccessException e) {
            respond(new Message("Exception", e), session);
        }


    }


    private void respond(Message message, Session session) {
        session.getAsyncRemote().sendText(message.toJson());
    }

    private Object[] cleanData(Method method, Object[] data) {

        if (data != null) {
            Gson gson = new Gson();
            for (int i = 0; i < data.length; i++) {

                data[i] = gson.fromJson(gson.toJson(data[i]), method.getParameterTypes()[i]);

            }
        }
        return data;
    }

    public Object call(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

            if (args == null) {
                return method.invoke(target);
            } else {
                return method.invoke(target, args);
            }


    }

    public void call(String methodName, Object[] args) throws Exception {
        call(getMethod(methodName), args);
    }

    private Method getMethod(String messageName) {
        for (Method m : methods) {
            if (m.getName().equals(messageName)) {
                return m;
            }
        }
        return null;

    }

    public Session getCaller() {
        return caller;
    }

}
