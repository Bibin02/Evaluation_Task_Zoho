import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class CMDBLibrary {

    public void sendRequestToCMDB(String CMDB, String apiKey, String object_id, String fields, HttpURLConnection connection) throws IOException{
        
        switch (CMDB) {
            case "SDP" -> {
                // Setting Headers
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authtoken", apiKey);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                // Handler for handling Requests and Response.
                CMDBRequestResponseHandler reqreshandler = new CMDBRequestResponseHandler();
                
                Map<String, String> params = new HashMap<>();
                StringBuilder returnFields = new StringBuilder();
                for (String fieldString : fields.split(",")) {
                    returnFields.append("<name>"+fieldString+"</name>");
                }
                params.put("cmdb", CMDB);
                params.put("ReturnFields", returnFields.toString() );

                reqreshandler.sendRequest(connection.getOutputStream(), params);
            }

            case "i-doit" -> {
                // Setting Headers
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Handler for handling Requests and Response.
                CMDBRequestResponseHandler reqreshandler = new CMDBRequestResponseHandler();
                
                Map<String, String> params = new HashMap<>();
                params.put("cmdb", CMDB);
                params.put("apikey", apiKey);
                params.put("object_id", object_id);
                params.put("language", "en");
                params.put("method", "cmdb.object.read");

                reqreshandler.sendRequest(connection.getOutputStream(), params);
            }

            case "snipe-it" -> {
                // Setting Headers
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZTU2MDc0MjVmYjM5YTEwYjFjNTZlZTAxMTBmZDk4ZjQ0ZjVjODMzYjcxZWVhYjZlNDk1NGMwOThlY2YzMzU2MDY4Mzg4MmFhMDMzOTAzNzciLCJpYXQiOjE2MzI4NjU5MTgsIm5iZiI6MTYzMjg2NTkxOCwiZXhwIjoyMjY0MDIxNTE4LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.LgGVzyH67IRhXvccHd4j2Dn6TDuIuQTBoo30_wD9jPehy8v_h0xBmE1-dOUBRJyeJOI8B4gwPeALsWaudpGj9Lb5qWAtKV7eYtH9IYQKoLF_iHgOGXnAUcNwID6zBU_YyLNSI6gp8zjutLJias33CBLsHy5ZRNpxVibVrZouJ_HjYuIYbtZyLus-KFFeibtZoPiTWOeHhQFD37MR6ifx4dBqT37fN-xDS99mONtrkAplEIou5aSO1oZ4IlJIPCUyA1lixPgpn1YU7PxiBDZp1teeugD0WEmrAqxRS2I0bH4qPsuTsrVXS_lo87Sf5LBGLW7lGHKqyYH6J47OZOM0K-SrxLKtE1ww8jyLBgnnxH0lJHRLCBiwUnL5ZGTUmiOysUA-wSJ6s78o8Pc-ec6bpBvAlelHdiQ-wslE7gzEJDptbejFg-75b_CEwgJYh7J2D18ul6Qu5EFCUEgt033mm04dgVk0isWTDt6EW5ZvTo5Qhr1LY0YnEIXCTqIRN-BSQjL55sZaCrtwR_21bnBGgniyI5MRDYblFawVmFKroeClCpSjBo9vi66akdD5hjpvx67RL3r33BZQhEXmPifUPNH5wP_U-IHGFUD99TJk2c1awF0RASveZRLSunbJb1x6hGAVUaIvQV4r2quWzXqYyKLph9kGTyJYrb6iJtH5smE");
                connection.setDoOutput(true);

            }

            case "cmdbuild" -> {
                // Setting Headers
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Cmdbuild-authorization", apiKey);
                connection.setDoOutput(true);
            }
        
        }
    }

}
