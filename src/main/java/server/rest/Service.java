package server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Path("")
public class Service {

    private static Map<String, RestPlayerData> playerHashMap = new HashMap<>();


    @GET
    @Path("users/login/{username}/{password}")
    public Response login(@PathParam("username") String username,
                          @PathParam("password") String password) {

        boolean valid = true;
        String message = "";

        RestPlayerData playerData = playerHashMap.get(generateUUID(username, password));
        if(playerData==null){
            valid = false;
            message = "Incorrect login information";
        }
        RestResponse<RestPlayerData> response = new RestResponse<>(valid, message, playerData);
        return response.build();
    }


    @GET
    @Path("users/register/{username}/{password}")
    public Response register(@PathParam("username") String username,
                             @PathParam("password") String password) {

        boolean valid = true;
        String message = "";
        for (RestPlayerData playerData : playerHashMap.values()) {
            if (playerData.getUsername().equals(username)) {
                valid = false;
                message = "Name is already registered";
            }
        }
        if(valid) {
            String id = generateUUID(username, password);
            playerHashMap.put(id, new RestPlayerData(username));
        }
        return new RestResponse<>(valid, message, valid).build();
    }


    private String generateUUID(String username, String password) {
        byte[] bytes = (username + "||" + password).getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString();
    }
}