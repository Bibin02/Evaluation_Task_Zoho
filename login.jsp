<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Open AI Login</title>
    <link rel="stylesheet" href="./styles/index.css">
</head>
<body>

    <%
        String sessionAttr = (String)session.getAttribute("login");

        if(sessionAttr != null){
            if(sessionAttr.equals("yes")){
                response.sendRedirect("./welcome-user.jsp");
            }
            // Enter into this Page.
        }
        
        // Enter into this Page.


    %>
    <form class="form-field" action="auth-user" method="post">
        <label for="uname">
            <input name="uname" id="uname" type="text" placeholder="UserName" required >
        </label><br>
        <label for="pass"></label>
            <input name="pass" id="pass" type="password" placeholder="Password" required >
        </label><br>

        <%
            String logMessage = (String)session.getAttribute("msg");
            if(logMessage != null){
                out.println("<div class=\"log-msg\">"+ logMessage +"</div>");
            }
        %>

        <input class="submit-button" type="submit" value="Login">
        <a href="./index.jsp"><button type="button" class="submit-button">Home</button></a>
    </form>
</body>
</html>