import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        HttpSession session = req.getSession();
        String apikey = (String)session.getAttribute("apikey");
        
        if (new OpenAIUserAuthorizer().signOut(apikey)) {
            session.setAttribute("login", "no");
            session.setAttribute("url", null);
            session.setAttribute("apikey", null);
            session.setAttribute("msg", "Logout Successfully ...");
            res.sendRedirect("./login.jsp");
            return;
        }
        else{
            res.sendRedirect("./login.jsp");
            return;
        }
        
    }
    
}