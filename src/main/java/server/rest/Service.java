package server.rest;

import com.google.gson.Gson;
import shared.rest.CSAbsResponse;
import shared.rest.SLoginResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/login")
public class Service {
    ArrayList<SLoginResponse> users = MejnServer.users;

    @GET
    @Path("/{value}")
    public Response getMsg(@PathParam("value") String msg) {
        /**
        CSAbsResponse response = new CSAbsResponse();
        response.setOperation("abs");
        response.setExpression(msg);
        try {
            int value = Integer.parseInt(msg);
            int result = (value<0)? -value:value;
            response.setResult(Integer.toString(result));
        } catch (NumberFormatException nfe) {
            response.setResult("invalid value");
        }
         **/

        SLoginResponse loginResponse = null;
        if(msg.contains("register")) {
            String[] parts = msg.split("-");
            int userindex = -1;
            boolean exist = false;
            for(int i = 0; i < users.size(); i++){
                if(users.get(i).getUsername().equals(parts[1])){
                    userindex = i;
                    System.out.println("true");
                    exist = true;
                } else{
                    System.out.println(parts[1] + " is not the same as " + users.get(i).getUsername());
                }
            }
            if(exist){
                if(parts[2] == users.get(userindex).getPassword()) {
                    loginResponse = users.get(userindex);
                    loginResponse.setResult("logged in");
                } else{
                    loginResponse = users.get(userindex);
                    loginResponse.setResult("incorrect password");
                }

            } else {
                System.out.println("false");
                loginResponse = new SLoginResponse();
                loginResponse.setUsername(parts[1]);
                loginResponse.setPassword(parts[2]);
                loginResponse.setResult("registered");
                users.add(loginResponse);
            }
        } else {
            loginResponse = new SLoginResponse();
            loginResponse.setUsername(msg);
            loginResponse.setResult("success");
        }
        Gson gson = new Gson();
        String output = gson.toJson(loginResponse);
        return Response.status(200).entity(output).build();
    }
}