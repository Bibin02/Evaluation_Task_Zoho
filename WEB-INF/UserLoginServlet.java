import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Login
 */
@WebServlet("/auth-user")
public class UserLoginServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        String username = req.getParameter("uname");
        String password = req.getParameter("pass");
        HttpSession session = req.getSession();

        if (username == null) {
            session.setAttribute("msg", "Session Expired Kindly Login Again ...");
            session.setAttribute("login", "no");
            res.sendRedirect("./login.jsp");
        }

        else if(new OpenAIUserAuthorizer().authorize(username, password)){
            session.setAttribute("login", "yes");
            session.setAttribute("msg", "Welcome "+username+" ...");
            res.sendRedirect("./get-api-key.jsp");
        }
        else{
            session.setAttribute("login", "no");
            session.setAttribute("msg", "User not Found / Invalid User Credentials ...");
            res.sendRedirect("./login.jsp");
        }
    }
    
}