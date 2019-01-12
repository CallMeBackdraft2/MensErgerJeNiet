package shared.interfaces;

public  interface ConnectionCalls {

    boolean login(String username, String password) throws Exception;

    boolean register(String username, String password) throws Exception;
}
