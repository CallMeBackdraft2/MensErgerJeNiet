package server.rest;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;

public class RestResponse <T> {

    private boolean succeeded;
    private String message;

    public RestResponse(boolean succeeded, String message, T response) {
        this.succeeded = succeeded;
        this.message = message;
        this.response = response;
    }

    private T response;

    public boolean isSucceeded() {
        return succeeded;
    }

    public Response build() {
        Gson gson = new Gson();
        String s = gson.toJson(this);
        return Response.status(200).entity(s).build();
    }

    public String getMessage() {
        return message;
    }
}
