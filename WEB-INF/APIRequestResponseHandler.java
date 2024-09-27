import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import database.DBOperations;

/**
 * APIRequestResponseHandler
 */
public class APIRequestResponseHandler {

    private String endpoint;
    private String model;
    private String prompt;
    
    /**
     * For Chat Completions.
     * @param endpoint
     * @param model
     * @param prompt
     */
    public APIRequestResponseHandler(String endpoint, String model, String prompt){
        this.endpoint = endpoint;
        this.model = model;
        this.prompt = prompt;
    }

    /**
     * For model retrieval.
     * @param endpoint
     * @param model
     */
    public APIRequestResponseHandler(String endpoint, String model){
        this.endpoint = endpoint;
        this.model = model;
    }

    /**
     * 
     * @param model
     * @param prompt
     * @param outputStream
     * @throws IOException 
     */
    public void sendRequest(OutputStream outputStream) throws IOException{
        
        JSONObject jsonRequest = new DBOperations().getRequestJSON(endpoint);
        String requestMessage = null;
        if (this.prompt == null) {
            requestMessage = setupModelRequestJSON(jsonRequest, this.model);
        }
        else{
            requestMessage = setupChatCompletionJSON(jsonRequest, this.model, this.prompt, 0.6f);
        }
        // Send request
        byte[] input = requestMessage.getBytes("utf-8");
        outputStream.write(input, 0, input.length);
        outputStream.close();
        
    }

    @SuppressWarnings("unchecked")
    private String setupChatCompletionJSON(JSONObject jsonRequest, String model, String prompt,
            float temperature){

        // For HuggingFace Structure
        if (jsonRequest.get("input") != null) {
            
        }
        // For OpenAI Structure
        // Create the JSON body for the request
        /* 
        curl https://api.openai.com/v1/chat/completions
            -H "Content-Type: application/json"
            -H "Authorization: Bearer $OPENAI_API_KEY"
            -d '{
                    "model": "gpt-4o",
                    "messages": [
                        {"role": "user", "content": "Contents or prompt we give .."}
                    ]
                    "temperature": 0.6,
                }'
        */
        else{
            JSONObject jsonObjectMessage = new JSONObject();
            JSONArray message = new JSONArray();

            jsonRequest.put("model", model);

            // Json Array for Messages
            jsonObjectMessage.put("role", "user");
            jsonObjectMessage.put("content", prompt);
            message.add(jsonObjectMessage);

            jsonRequest.put("messages", message);
            jsonRequest.put("temperature", temperature); // Setting the temperature
        }
        

        return jsonRequest.toString();
    }

    /**
     * 
     * @param jsonRequest
     * @param model
     * @return
     */
    private String setupModelRequestJSON(JSONObject jsonRequest, String model){ return null; }

    /**
     * 
     * @param inputStream
     * @return response Data from the URL.
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public String getResponse(InputStream inputStream) throws UnsupportedEncodingException, IOException{
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
        return getReplyMessage(response);
    }

    /**
     * 
     * @param response
     * @return chat completion text.
     */
    private String getReplyMessage(StringBuilder response){
        
        JSONObject responsejson = (JSONObject) JSONValue.parse(response.toString());

        // System.out.println(responsejson +"  "+ response.toString());

        JSONObject choices0 = (JSONObject)(((JSONArray)(responsejson.get("choices"))).get(0));

        // FOr NON - Standard Outputs.
        if (choices0 == null) {
            return null;
        }

        // For Standard OpenAI output Formats
        String content = (String) (((JSONObject)(choices0.get("message"))).get("content"));

        return content;
    }
}