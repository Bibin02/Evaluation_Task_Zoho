import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.annotation.WebServlet;


/**
 * Service Desk Plus Cofiguration Management DB
 * Critical and Vulnerable Assets fetching Servlet.
 * 
 * 
 * @since 1.0
 */
@WebServlet("/CMDB-CV-Assets")
public class CMDBCVAssets extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");

        ServletInputStream inpStream = req.getInputStream();
        Object requestBody = JSONValue.parse(new String(inpStream.readAllBytes()));
        if (requestBody == null) {
            // Invalid request
            out.println("{ \"message\": \"Invalid Request due to missing JSON Body\" } ");
            return;
        }
        JSONObject requestJSON = (JSONObject) requestBody;
        String cmdb = (String)requestJSON.get("cmdb");
        String apiKey = (String)requestJSON.get("apikey");
        String object_id = (String)requestJSON.get("object_id");
        String fields = (String)requestJSON.get("fields");

        if (cmdb.equals("SDP") || cmdb.equals("i-doit")) {
            if (apiKey.equals("")) {
                // No APIKEY Found
                out.println("{ \"message\": \"No APIKEY Given.\" } ");
                return; 
            }
            else if ((cmdb.equals("i-doit") || cmdb.equals("snipe-it")) && object_id.equals("")) {
                // No Object ID Given
                out.println("{ \"message\": \"No Object ID Given.\" } ");
                return; 
            }
        }
        if (cmdb.equals("cmdbuild")) {
            apiKey = (String)req.getSession().getAttribute("cmdbuild-apikey");
            if (apiKey == null) {
                apiKey = new CallAPI().getCmdbBuildToken();
                req.getSession().setAttribute("cmdbuild-apikey", apiKey);
            }
        }
        // Request API
        String responseJSON = new CallAPI().get_CV_CMDB_Assets(cmdb, apiKey, object_id, fields);
        out.println(new CMDBRequestResponseHandler().reStructureJSON(cmdb, responseJSON));
        return;
    }
    
}