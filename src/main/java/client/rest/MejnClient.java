package client.rest;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * The RESTNewtonAPICalculator implements the ICalculator interface. It uses a REST Service that provides a client with the
 * absolute value for an integer number
 */
public class MejnClient{

    public static void main(String[] args){
        MejnClient shipclient = new MejnClient();
        shipclient.register("Leeroy", "Jennnnnkkkkkkiiiiiiinnnnnnsssssssss");
    }

    class Response {

        private String operation = "n/a";
        private String expression = "n/a";
        private String result = "n/a";

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    class LoginResponse{
        private String username = "";
        private String password = "";
        private String result = "";

        public void setUsername(String username){ this.username = username; }
        public void setResult(String result){ this.result = result;}

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getResult() { return result; }
    }

    public int abs(int value) {
        int result = Integer.MIN_VALUE;

        // Build the query for the
        //
        //  https://github.com/aunyks/newton-api
        //
        final String query = "http://localhost:8090/login/" + Integer.toString(value);

        System.out.println("[Query] : " + query);

        // Perform the query
        HttpGet httpGet = new HttpGet(query);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            Gson gson = new Gson();
            Response jsonResponse = gson.fromJson(entityString,Response.class);
            String stringResult = jsonResponse.getResult();
            System.out.println("[Result] : " + stringResult );
            result = Integer.parseInt(stringResult);
        } catch (IOException e) {
            System.out.println("IOException : " + e.toString());
        }

        return result;
    }

    public String login(String username) {
        //int result = Integer.MIN_VALUE;
        String result = "";
        // Build the query for the
        //
        //  https://github.com/aunyks/newton-api
        //
        final String query = "http://localhost:8090/login/" + username;

        System.out.println("[Query] : " + query);

        // Perform the query
        HttpGet httpGet = new HttpGet(query);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            Gson gson = new Gson();
            LoginResponse jsonResponse = gson.fromJson(entityString,LoginResponse.class);
            String stringResult = jsonResponse.getResult();
            System.out.println("[Result] : " + stringResult );
            result = stringResult;
        } catch (IOException e) {
            // Evil, pure evil this solution: ....
            System.out.println("IOException : " + e.toString());
        }

        return result;
    }

    public String register(String username, String password){

        String result = "";
        // Build the query for the
        //
        //  https://github.com/aunyks/newton-api
        //
        final String query = "http://localhost:8090/login/register-" + username + "-" + password;

        System.out.println("[Query] : " + query);

        // Perform the query
        HttpGet httpGet = new HttpGet(query);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            System.out.println("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println("[Entity] : " + entityString);
            Gson gson = new Gson();
            LoginResponse jsonResponse = gson.fromJson(entityString,LoginResponse.class);
            String stringResult = jsonResponse.getResult();
            System.out.println("[Result] : " + stringResult );
            result = stringResult;
        } catch (IOException e) {
            // Evil, pure evil this solution: ....
            System.out.println("IOException : " + e.toString());
        }

        return result;
    }
}
