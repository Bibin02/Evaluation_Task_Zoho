import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CMDBRequestResponseHandler {

    public void sendRequest(OutputStream outputStream, Map<String, String> params) throws IOException{
        String requestMessage = setRequestMessage(params);

        // Send request
        byte[] input = requestMessage.getBytes("utf-8");
        outputStream.write(input, 0, input.length);
        outputStream.close();
    }

    private String setRequestMessage(Map<String, String> params) {
        String requestMessage = null;

        switch (params.get("cmdb")) {
            case "SDP" -> {
                requestMessage = 
                "INPUT_DATA=<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
                "<API version=\"1.0\" locale=\"en\">" + 
                    "<citype>" + 
                        "<name>All Assets</name>" + 
                        "<criterias>" + 
                        "<criteria>" + 
                            "<parameter>" + 
                                "<name compOperator=\"IS\">Business Impact</name>" + 
                                "<value>High</value>" + 
                            "</parameter>" + 
                            "<reloperator>AND</reloperator>" + 
                            "<parameter>" + 
                                "<name compOperator=\"CONTAINS\">Asset State</name>" + 
                                "<value>In Repair</value>" + 
                            "</parameter>" + 
                            "<reloperator>OR</reloperator>" +
                            "<parameter>" +
                                "<name compOperator=\"LESSER THAN\">Warranty Expiry Date</name>" +
                                "<value>"+new java.util.Date().getTime()+"</value>" +
                            "</parameter>" +
                        "</criteria>" + 
                        "</criterias>" + 
                        "<returnFields>" + 
                            params.get("ReturnFields") + 
                        "</returnFields>" + 
                    "</citype>" + 
                "</API>"+

                "&OPERATION_NAME=read";
            }

            case "i-doit" -> {
                requestMessage = 
                "{" +
                    "\"version\": \"2.0\"," +
                    "\"method\": \""+params.get("method")+"\"," +
                    "\"params\": {" +
                        "\"id\": "+params.get("object_id")+"," +
                        "\"apikey\": \""+params.get("apikey")+"\"," +
                        "\"language\": \""+params.get("language")+"\"" +
                    "}," +
                "\"id\": 1" +
                "}";
            }

            case "cmdbuildtoken" -> {
                requestMessage =
                "{" + 
                    "\"username\": \"admin\"," + 
                    "\"password\": \"admin\"" + 
                "}";
            }
        }

        return requestMessage;
    }

    @SuppressWarnings("unchecked")
    public String reStructureJSON(String CMDB, String responseJSON) {

        JSONObject respJsonObject = (JSONObject)JSONValue.parse(responseJSON), outputJSON = new JSONObject();

        // If Exception occured in HttpURLConnection due to Wrong API request / General Exceptions, 
        if (respJsonObject.get("message") != null) {
            return respJsonObject.toJSONString();
        }

        String responseString = "";

        switch (CMDB) {
            case "SDP" -> {
                JSONObject Details = (JSONObject)(((JSONObject)((JSONObject)((JSONObject)(((JSONObject)respJsonObject
                                .get("API"))
                                .get("response")))
                                .get("operation")))
                                .get("Details"));

                Object headerArray = ((JSONObject)(Details.get("field-names"))).get("name");
                Object recordsArray = ((JSONObject)(Details.get("field-values"))).get("record");

                outputJSON.put("headerArray", headerArray);
                outputJSON.put("recordsArray",recordsArray);

                responseString = outputJSON.toJSONString();
            }

            case "i-doit" -> {
                JSONObject errorObject = (JSONObject) respJsonObject.get("error");
                if (errorObject != null) {
                    return ("{ \"message\": \""+(String)errorObject.get("message")+"\" } ");
                }
                else if (respJsonObject.get("result") instanceof JSONArray) {
                    return ("{ \"message\": \" No Objects found for the given Object ID\" } ");
                }
                JSONObject resultObject = (JSONObject) respJsonObject.get("result");

                // Create output JSON structure
                JSONArray headerArray = new JSONArray();
                JSONArray recordsArray = new JSONArray();

                // Dynamically load headers
                for (Object key : resultObject.keySet()) {
                    JSONObject headerObj = new JSONObject();
                    headerObj.put("type", "String");
                    headerObj.put("content", key.toString());
                    headerArray.add(headerObj);
                }

                // Load record (since there is only one object, we will load it into one record)
                JSONArray valueArray = new JSONArray();
                for (Object key : resultObject.keySet()) {
                    Object value = resultObject.get(key);
                    valueArray.add(value != null ? value.toString() : "null");
                }
                JSONObject recordObj = new JSONObject();
                recordObj.put("value", valueArray);
                recordsArray.add(recordObj);

                // Final output structure
                outputJSON.put("headerArray", headerArray);
                outputJSON.put("recordsArray", recordsArray);

                responseString = outputJSON.toJSONString();
            }

            case "cmdbuild" -> {
                JSONArray dataArray = (JSONArray) respJsonObject.get("data");

                // Create output JSON structure
                JSONArray headerArray = new JSONArray();
                JSONArray recordsArray = new JSONArray();

                // Dynamically load headers
                if (dataArray.size() > 0) {
                    JSONObject firstObject = (JSONObject) dataArray.get(0);
                    for (Object key : firstObject.keySet()) {
                        JSONObject headerObj = new JSONObject();
                        headerObj.put("type", "String");
                        headerObj.put("content", key.toString());
                        headerArray.add(headerObj);
                    }
                }

                // Load records
                for (Object obj : dataArray) {
                    JSONObject record = (JSONObject) obj;
                    JSONArray valueArray = new JSONArray();
                    for (Object key : record.keySet()) {
                        Object value = record.get(key);
                        valueArray.add(value != null ? value.toString() : "null");
                    }
                    JSONObject recordObj = new JSONObject();
                    recordObj.put("value", valueArray);
                    recordsArray.add(recordObj);
                }

                // Final output structure
                outputJSON.put("headerArray", headerArray);
                outputJSON.put("recordsArray", recordsArray);

                responseString = outputJSON.toJSONString();
            }

            case "cmdbuildtoken" -> {
                String Token = (String)(((JSONObject)(respJsonObject.get("data"))).get("_id"));
                if(Token != null){
                    System.out.println(Token);
                    return Token;
                }
                else{
                    return null;
                }
            }

            case "snipe-it" -> {
                // Create output JSON structure
                JSONArray headerArray = new JSONArray();
                JSONArray recordsArray = new JSONArray();
                
                // Prepare valueArray for records
                JSONArray valueArray = new JSONArray();

                // Process the JSON object and load headers and values
                processJsonObject(respJsonObject, headerArray, valueArray);

                // Add the value array to recordsArray in the required structure
                JSONObject recordObj = new JSONObject();
                recordObj.put("value", valueArray);
                recordsArray.add(recordObj);

                // Final output structure
                outputJSON.put("headerArray", headerArray);
                outputJSON.put("recordsArray", recordsArray);

                responseString = outputJSON.toJSONString();
            }
            

        }
        return responseString;
    }

    @SuppressWarnings("unchecked")
    // Helper method to handle nested JSON objects recursively
    private static void processJsonObject(JSONObject jsonObject, JSONArray headerArray, JSONArray valueArray) {
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            
            // Add to header array
            JSONObject headerObj = new JSONObject();
            headerObj.put("type", "String");
            headerObj.put("content", key.toString());
            headerArray.add(headerObj);
            
            // Check if value is a nested JSON object
            if (value instanceof JSONObject) {
                // If it's a nested object, recursively process it
                processJsonObject((JSONObject) value, headerArray, valueArray);
            } else if (value instanceof JSONArray) {
                // Handle arrays as well (in case there are any)
                JSONArray arrayValue = (JSONArray) value;
                valueArray.add(arrayValue.toJSONString());
            } else {
                // Handle normal values, add to valueArray
                valueArray.add(value != null ? value.toString() : "null");
            }
        }
    }
}
