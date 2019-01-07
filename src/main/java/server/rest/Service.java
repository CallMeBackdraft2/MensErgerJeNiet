package server.rest;

import client.domain.classes.Player;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.nameUUIDFromBytes;

@Path("")
public class Service {

    private static Map<String, Player> playerHashMap = new HashMap<>();


    @GET
    @Path("users/login/{username}/{password}")
    public Response login(@PathParam("username") String username,
                          @PathParam("password") String password) {

        Player p = playerHashMap.get(generateUUID(username,password));

        return Response.status(200).entity(p.getName()).build();
    }


    private String generateUUID(String username, String password){
        byte[] bytes =  (username+"||"+password).getBytes();
       UUID uuid = UUID.nameUUIDFromBytes(bytes);
       return uuid.toString();
    }

    @GET
    @Path("users/register/{username}/{password}")
    public Response register(@PathParam("username") String username,
                             @PathParam("password") String password) {


        String id =  generateUUID(username,password);
        playerHashMap.put(id,new Player(username));
        return Response.status(200).entity(id).build();
    }
}