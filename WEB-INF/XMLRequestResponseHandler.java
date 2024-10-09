import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class XMLRequestResponseHandler {

    public void sendRequest(OutputStream outputStream, Map<String, String> params) throws IOException{
        String requestMessage = setRequestMessage(params);
        // Send request
        byte[] input = requestMessage.getBytes("utf-8");
        outputStream.write(input, 0, input.length);
        outputStream.close();
    }

    private String setRequestMessage(Map<String, String> params) {
        String requestMessage = null;

        if (params.get("AssetType").equals("CV")) {
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
        

        // System.out.println(requestMessage);

        return requestMessage;
    }
}
