import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * ApikeyRemoveServlet
 */
@WebServlet("/rem-apikey")
public class ApikeyRemoveServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        HttpSession session = req.getSession();
        String isLogin = (String)session.getAttribute("login");

        if (isLogin != null) {
            if (isLogin.equals("no")) {
                res.sendRedirect("./login.jsp");
                return;
            }
            else {
                session.setAttribute("url", null);
                session.setAttribute("apikey", null);
                session.setAttribute("msg", "APIKEY flush Success! Try Using another APIKEY !");
                res.sendRedirect("./get-api-key.jsp");
                return;
            }
        }
        else{
            res.sendRedirect("./login.jsp");
            return;
        }
        
    }
    
}