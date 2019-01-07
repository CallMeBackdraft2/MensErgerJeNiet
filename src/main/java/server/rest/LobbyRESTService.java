package server.rest;

import client.domain.classes.Lobby;
import server.logic.LobbyManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lobby")
public class LobbyRESTService {
    public LobbyRESTService() {

    }

    @POST
    @Path("/lobby")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addLobby(Lobby lobby) {

        System.out.println("[Server addLobby]");

        // Check request
        if (lobby == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }

        // Add Lobby to the list
        LobbyManager.getInstance().getLobbies().add(lobby);

        // Define response
        return Response.status(200).entity(RestResponseHelper.getSingleLobbyResponse(lobby)).build();
    }

    @GET
    @Path("/lobby/{lobbyid}")
    @Produces("application/json")
    public Response GetLobby(@PathParam("lobbyid") String lobbyIdAsString) {

        System.out.println("[Server GetLobby]");

        // Find lobby
        int lobbyId = Integer.parseInt(lobbyIdAsString);
        Lobby lobby = LobbyManager.getInstance().getLobbies().get(lobbyId);

        // Check whether lobby exists
        if (lobby == null) {
            // Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }

        // Define response
        return Response.status(200).entity(RestResponseHelper.getSingleLobbyResponse(lobby)).build();
    }

    @GET
    @Path("/lobby/all")
    @Produces("application/json")
    public Response getAllLobbies() {

        System.out.println("[Server getAllLobbies]");

        // Get all pets from the store
        List<Lobby> allLobbiesFromRest = LobbyManager.getInstance().getLobbies();

        // Define response
        return Response.status(200).entity(RestResponseHelper.getAllLobbiesResponse(allLobbiesFromRest)).build();
    }

}
