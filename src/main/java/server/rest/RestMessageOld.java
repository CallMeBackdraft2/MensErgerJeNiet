package server.rest;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;

public class RestMessageOld {

    public boolean succeeded() {
        return succeeded;
    }

    private boolean succeeded;

    public String getMessage() {
        return message;
    }

    private String message;

    public RestMessageOld(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RestMessageOld(boolean succeeded, String message) {
        this.succeeded = succeeded;
        this.message = message;
    }

    public Response build() {
        Gson gson = new Gson();
        String s = gson.toJson(this);
        return Response.status(200).entity(s).build();
    }

}
