<%@page contentType="text/html" import="java.util.*" import="database.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My OpenAI API Gateway</title>
    <link rel="stylesheet" href="./styles/index.css">
</head>
<body>

    <%
        String sessionAttr = (String)session.getAttribute("login");

        if(sessionAttr != null){
            if(sessionAttr.equals("yes")){
                // Enter into this Page.
            }
            else{
                response.sendRedirect("./login.jsp");
            }
        }
        else{
            response.sendRedirect("./login.jsp");
        }

        sessionAttr = (String)session.getAttribute("apikey");

        if(sessionAttr != null){
            response.sendRedirect("./eventlogdebugger.jsp");
        }
        // Enter into this Page.

    %>
    <form class="form-field" action="auth-key" method="post">
        <select class="url-select" name="url" id="url">
            <%
                ArrayList<ArrayList<String>> models = new DBOperations().getModelsForUser();

                for(int i=0; i<models.size(); i++){
                    out.println("<option value=\""+models.get(i).get(0)+"\">"+ models.get(i).get(1)+"</option>");
                }
            %>
        </select>
        <label for="key">
            <input name="key" id="key" type="password" placeholder="API Key" required >
        </label><br>

        <%
            String logMessage = (String)session.getAttribute("msg");
            if(logMessage != null){
                out.println("<div class=\"log-msg\">"+ logMessage +"</div>");
            }
        %>

        <input class="submit-button" type="submit" value="Login">
        <a href="./welcome-user.jsp"><button type="button" class="submit-button"> Back </button></a>
    </form>
</body>
</html>