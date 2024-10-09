import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    public CallAPI(String endpoint, String apiKey, String fields){
        this.apiargs.put("endpoint", endpoint);
        this.apiargs.put("apiKey", apiKey);
        this.apiargs.put("fields", fields);
    }

    public String initGet_CV_CMDB_Assets(){
        try{
            setupIgnoreSSLCertificate();
            URL url = new URI(this.apiargs.get("endpoint")).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Setting Headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authtoken", this.apiargs.get("apiKey"));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            // Handler for handling Requests and Response.
            XMLRequestResponseHandler reqreshandler = new XMLRequestResponseHandler();
            
            Map<String, String> params = new HashMap<>();
            StringBuilder returnFields = new StringBuilder();
            for (String fieldString : this.apiargs.get("fields").split(",")) {
                returnFields.append("<name>"+fieldString+"</name>");
            }
            params.put("AssetType", "CV");
            params.put("ReturnFields", returnFields.toString() );

            reqreshandler.sendRequest(connection.getOutputStream(), params);

            // Exception in Connection Handling
            exceptionThrowable(connection);

            // Return the Response for the Prompt.
            return getResponse(connection.getInputStream());

        }
        catch (IOException | URISyntaxException ioe){
            return ("{ \"message\": \""+ioe.getLocalizedMessage()+"\" } ");
        }
    }

    private void setupIgnoreSSLCertificate() {

        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException{
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException{
                    }
                }
            };
    
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
    
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
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
            return reqreshandler.getReplyMessage(getResponse(connection.getInputStream()));

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

    private String getResponse(InputStream inputStream) throws IOException{
        // Read the response
        StringBuilder response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
            response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        // Return Prompt Message
        return response.toString();
    }

    private void exceptionThrowable(HttpURLConnection connection) throws IOException{
        if(connection.getResponseCode() != 200){
            throw new IOException("HTTP Response Code : "+connection.getResponseCode()+"\n HTTP Response Message : "+connection.getResponseMessage());
        }
    }
}
