import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.annotation.WebServlet;

import database.DBOperations;

/**
 * Service Desk Plus Cofiguration Management DB
 * Critical and Vulnerable Assets fetching Servlet.
 * 
 * 
 * @since 1.0
 */
@WebServlet("/SDP-CMDB-CV-Assets")
public class SDPCMDBCVAssets extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");

        String apiKey = (String)req.getParameter("apikey");
        String fields = (String)req.getParameter("fields");

        if (apiKey != null) {
            if (new DBOperations().validateAPIDB(apiKey, "sdp-cv-a")) {
                requestAPIProcess(apiKey, fields, out);
                return;
            }
        }

        // Invalid request
        out.println("{ \"message\": \"Invalid Request No API Key found\" } ");
        return;
    }

    private void requestAPIProcess(String apiKey, String fields, PrintWriter out) {
        
        String responseJSON = new CallAPI("https://localhost:7000/api/cmdb/ci", apiKey, fields).initGet_CV_CMDB_Assets();

        out.println(resstructureJSON(responseJSON));
    }

    @SuppressWarnings("unchecked")
    private String resstructureJSON(String responseJSON) {

        JSONObject respJsonObject = (JSONObject)JSONValue.parse(responseJSON), outputJSON = new JSONObject();

        JSONObject Details = (JSONObject)(((JSONObject)((JSONObject)((JSONObject)(((JSONObject)respJsonObject
                            .get("API"))
                            .get("response")))
                            .get("operation")))
                            .get("Details"));
        
        Object headerArray = ((JSONObject)(Details.get("field-names"))).get("name");
        Object recordsArray = ((JSONObject)(Details.get("field-values"))).get("record");

        outputJSON.put("headerArray", headerArray);
        outputJSON.put("recordsArray",recordsArray);

        return outputJSON.toJSONString();
    }
    
}