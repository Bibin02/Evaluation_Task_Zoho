import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * NotFoundServlet
 */
@WebServlet("/not-found")
public class NotFoundServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html><head>");
        out.println("<title>Not Found</title>" + // Page Title
                    "<link rel=\"stylesheet\" href=\"/OpenAI/styles/index.css\">" + // CSS link.
                    "</head><body>");

        out.println("<h1 style=\"text-align: center;\"> The Page You are Trying to Search was not found. </h1>");
        out.println("<a href=\"/OpenAI/index.jsp\"><button class=\"submit-button form-field\" type=\"button\">Home</button></a>");
        out.println("</body></html>");
    }
    
}