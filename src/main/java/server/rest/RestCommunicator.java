package server.rest;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RestCommunicator {

    private CloseableHttpClient httpClient;
    private String basePath;
    private Gson gson = new Gson();

    public RestCommunicator(String basePath) {
        this.basePath = basePath;
        httpClient = HttpClients.createDefault();

    }


    public RestResponse getRequest(String controller, String method, String... parameters) {


        String path = getPath(controller, method, parameters);
        CloseableHttpResponse response;

        try {
            response = httpClient.execute(new HttpGet(path));
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            return gson.fromJson(entityString, RestResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public RestResponse postRequest(String controller, String method, String... parameters) {

        String path = getPath(controller, method, parameters);
        CloseableHttpResponse response;

        try {
            response = httpClient.execute(new HttpPost(path));
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            return gson.fromJson(entityString, RestResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String getPath(String controller, String method, String[] parameters) {
        String path;
        StringBuilder pathBuilder = new StringBuilder();

        pathBuilder.append(basePath);
        pathBuilder.append(controller).append("/");
        pathBuilder.append(method);

        for (Object parameter : parameters) {
            pathBuilder.append("/").append(parameter);
        }
        path = pathBuilder.toString();
        return path;
    }


}
