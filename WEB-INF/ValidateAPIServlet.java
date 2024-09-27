import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Login
 */
@WebServlet("/auth-key")
public class ValidateAPIServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        String url = req.getParameter("url");
        String apiKey = req.getParameter("key");
        HttpSession session = req.getSession();
        String login = (String)session.getAttribute("login");

        if (login == null) {
            session.setAttribute("msg", "Session Expired Kindly Login Again ...");
            session.setAttribute("login", "no");
            res.sendRedirect("./login.jsp");
        }
        
        else if (login.equals("no")) {
            session.setAttribute("msg", "Kindly Login ...");
            res.sendRedirect("./login.jsp");
        }

        else if(new OpenAIUserAuthorizer().validateAPI(apiKey, url)){
            session.setAttribute("url", url);
            session.setAttribute("apikey", apiKey);
            session.setAttribute("msg", session.getAttribute("msg")+" and API Key Authorized !");
            res.sendRedirect("./eventlogdebugger.jsp");
        }
        else{
            session.setAttribute("msg", "Invalid API Key use another API Key ...");
            res.sendRedirect("./get-api-key.jsp");
        }
    }
    
}