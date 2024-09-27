import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * PromptServlet
 */
@WebServlet("/prompt")
public class PromptServlet extends HttpServlet{

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{

        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();
        String sessionLogin = (String) req.getSession().getAttribute("login");
        String url = (String)req.getSession().getAttribute("url");
        String apiKey = (String)req.getSession().getAttribute("apikey");


        if (sessionLogin != null && apiKey != null){
            if (sessionLogin.equals("yes")) {

                String prompt = (String)req.getParameter("prompt");

                if (prompt == null) {
                    out.println("You Don't Have Access to this Page Directly!\n Kindly Use Prompt Button to get Responses.");
                    return;
                }
                
                String model = (String)req.getParameter("gpt-model");
                String preprompt = (String)session.getAttribute("prepro");
                String preresponse = (String)session.getAttribute("preres");

                // Preventing Reload as new Prompt.
                if (preprompt != null) {
                    if (preprompt.equals(prompt)) {
                        req.setAttribute("pro", prompt);
                        req.setAttribute("res", preresponse);
                        req.getRequestDispatcher("./open-ai-prompt.jsp").include(req, res);
                        return;
                    }
                }

                // Call other Servlet to get Response
                String promptResponse = new CallAPI(url, apiKey, prompt, model).initChatCall();

                session.setAttribute("prepro", prompt);
                session.setAttribute("preres", promptResponse);


                req.setAttribute("pro", prompt);
                req.setAttribute("res", promptResponse);
                req.getRequestDispatcher("./open-ai-prompt.jsp").include(req, res);
            }
            else{
                out.println("Kindly Login..");
            }
        }
        else{
            out.println("Session Expired ! ");
        }
    }
}