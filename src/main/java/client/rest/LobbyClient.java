package client.rest;

import client.domain.classes.LobbyView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import shared.rest.LobbyResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyClient {
    private final String url = "http://localhost:8090/lobby";

    private final Gson gson = new Gson();

    public LobbyClient() {

    }

    public LobbyView getLobby(int lobbyId) {
        String queryGet = "/lobby/" + lobbyId;
        LobbyResponse response = executeQueryGet(queryGet);
        return response.getLobbies().get(0);
    }

    public List<LobbyView> getAllLobbies() {
        String queryGet = "/lobby/all";
        LobbyResponse response = executeQueryGet(queryGet);
        return response.getLobbies();
    }

    public LobbyView addLobby(LobbyView lobby) {
        String queryPost = "/lobby";
        LobbyResponse response = executeQueryPost(lobby,queryPost);
        return response.getLobbies().get(0);
    }

    public boolean removeLobby(int lobbyId) {
        String queryDelete = "/lobby/" + lobbyId;
        LobbyResponse response = executeQueryDelete(queryDelete);
        return response.isSuccess();
    }

    private LobbyResponse executeQueryGet(String queryGet) {

        // Build the query for the REST service
        final String query = url + queryGet;
        System.out.println("[Query Get] : " + query);

        // Execute the HTTP GET request
        HttpGet httpGet = new HttpGet(query);
        return executeHttpUriRequest(httpGet);
    }

    private LobbyResponse executeQueryPost(LobbyView lobbyRequest, String queryPost) {

        // Build the query for the REST service
        final String query = url + queryPost;
        System.out.println("[Query Post] : " + query);

        // Execute the HTTP POST request
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(gson.toJson(lobbyRequest));
            httpPost.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LobbyResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeHttpUriRequest(httpPost);
    }

    private LobbyResponse executeQueryDelete(String queryDelete) {

        // Build the query for the REST service
        final String query = url + queryDelete;
        System.out.println("[Query Delete] : " + query);

        // Execute the HTTP DELETE request
        HttpDelete httpDelete = new HttpDelete(query);
        return executeHttpUriRequest(httpDelete);
    }

    private LobbyResponse executeHttpUriRequest(HttpUriRequest httpUriRequest) {

        // Execute the HttpUriRequest
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            LobbyResponse lobbyResponse = gson.fromJson(entityString, LobbyResponse.class);
            return lobbyResponse;
        } catch (IOException e) {
            System.out.println("IOException : " + e.toString());
            LobbyResponse petStoreResponse = new LobbyResponse();
            petStoreResponse.setSuccess(false);
            return petStoreResponse;
        } catch (JsonSyntaxException e) {
            System.out.println("JsonSyntaxException : " + e.toString());
            LobbyResponse lobbyResponse = new LobbyResponse();
            lobbyResponse.setSuccess(false);
            return lobbyResponse;
        }
    }
}
