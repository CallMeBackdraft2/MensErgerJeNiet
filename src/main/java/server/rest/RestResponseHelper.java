package server.rest;

import client.domain.classes.LobbyView;
import com.google.gson.Gson;
import shared.rest.LobbyResponse;

import java.util.ArrayList;
import java.util.List;

public class RestResponseHelper {
    private static final Gson gson = new Gson();

    public static String getErrorResponseString()
    {
        LobbyResponse response = new LobbyResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSingleLobbyResponse(LobbyView lobbyFromRest)
    {
        LobbyResponse response = new LobbyResponse();
        response.setSuccess(true);
        List<LobbyView> lobbies = new ArrayList<>();
        LobbyView lobby = lobbyFromRest;
        lobbies.add(lobby);
        response.setLobbies(lobbies);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getSuccessResponse(boolean success)
    {
        LobbyResponse response = new LobbyResponse();
        response.setSuccess(success);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }

    public static String getAllLobbiesResponse(List<LobbyView> allLobbies)
    {
        LobbyResponse response = new LobbyResponse();
        response.setSuccess(true);
        response.setLobbies(allLobbies);
        String output = gson.toJson(response);
        System.out.println("[Server response] " + output);
        return output;
    }
}
