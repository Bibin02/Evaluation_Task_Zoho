import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import database.DBOperations;

import javax.servlet.annotation.*;

/**
 * SignupServlet
 */
@WebServlet("/create-user")
public class SignupServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        String username = req.getParameter("uname");
        String password = req.getParameter("pass");
        HttpSession session = req.getSession();

        if (username == null) {
            session.setAttribute("msg", "Bad Request ...");
            session.setAttribute("login", "no");
            res.sendRedirect("./signup.jsp");
            return;
        }

        boolean isUserCreated = new DBOperations().createUser(username, password);

        if(isUserCreated){
            session.setAttribute("msg", "User Created Sucessfully, Kindly Login ...");
            res.sendRedirect("./login.jsp");
        }
        else{
            session.setAttribute("login", "no");
            session.setAttribute("msg", "User Already Exists ...");
            res.sendRedirect("./signup.jsp");
        }
    }
    
}