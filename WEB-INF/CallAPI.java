import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;

import database.DBOperations;
/**
 * CallAPI
 */
public class CallAPI{
    
    private Map<String, String> apiargs = new HashMap<>();

    public CallAPI(String url, String username, String password, String prompt, String model){
        this.apiargs.put("username", username);
        this.apiargs.put("password", password);
        this.apiargs.put("prompt", prompt);
        this.apiargs.put("model", model);
    }
    
    public CallAPI(String url, String apiKey, String prompt, String model) {
        this.apiargs.put("endpoint", new DBOperations().getOriginalURL(url));
        this.apiargs.put("apiKey", apiKey);
        this.apiargs.put("prompt", prompt);
        this.apiargs.put("model", model);
    }

    public String initChatCall() {
        try{

            if (this.apiargs.get("endpoint") == null) {
                throw new IOException("Invalid url");
            }

            // Prepare the OpenAI API request
            // URL url = new URL(endpoint); // Deprecated

            URL url = new URI(this.apiargs.get("endpoint")).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set Headers
            if (this.apiargs.get("apiKey") == null) {
                setHeaders(connection, this.apiargs.get("username"), this.apiargs.get("password"));
            }
            else{
                setHeaders(connection, this.apiargs.get("apiKey"));
            }

            // Handler for handling Requests and Response.
            APIRequestResponseHandler reqreshandler = null;
            // If request for models.
            if (this.apiargs.get("prompt") == null) {
                reqreshandler = new APIRequestResponseHandler(this.apiargs.get("endpoint"), this.apiargs.get("model"));
            }
            // Request for Chat completions.
            else{
                reqreshandler = new APIRequestResponseHandler(this.apiargs.get("endpoint"), this.apiargs.get("model"), this.apiargs.get("prompt"));
            }
            
            reqreshandler.sendRequest(connection.getOutputStream());
            // Exception in Connection Handling
            exceptionThrowable(connection);

            // Return the Response for the Prompt.
            return reqreshandler.getResponse(connection.getInputStream());

        }catch(IOException | URISyntaxException ioe){
            return "!!" + ioe.getMessage();
        }
    }

    // API Key based authorization,
    private void setHeaders(HttpURLConnection connection, String apiKey) throws ProtocolException{
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
    }

    // UserName Password Based Authorization.
    private void setHeaders(HttpURLConnection connection, String username, String password) throws ProtocolException{
        // Set request method
        connection.setRequestMethod("POST");

        String authHeaderValue = "Basic " + username + ":" + password;

        // Set request properties
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
    }

    private void exceptionThrowable(HttpURLConnection connection) throws IOException{
        if(connection.getResponseCode() != 200){
            throw new IOException("HTTP Response Code : "+connection.getResponseCode()+"\n HTTP Response Message : "+connection.getResponseMessage());
        }
    }
}
