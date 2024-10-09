<%@ page contentType="html" %>
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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webpage Signup</title>
    <link rel="stylesheet" href="./styles/index.css">
    <script type="module" src="./scripts/jquery-3.7.1.js"></script>
    <script type="module" src="./scripts/signup.js"></script>
</head>
<body>
    <form class="form-field" action="create-user" method="post">
        <label for="uname">
            <input name="uname" id="uname" type="text" placeholder="UserName" required >
        </label><br>
        <label for="pass"></label>
            <input name="pass" id="pass" type="password" placeholder="Password" required >
        </label><br>
        <label for="cnfpass"></label>
            <input name="cnfpass" id="cnfpass" type="text" placeholder="Confirm Password" required >
        </label><br>

        <%
            String logMessage = (String)session.getAttribute("msg");
            if(logMessage != null){
                out.println("<div class=\"log-msg\">"+ logMessage +"</div>");
            }
        %>
        <a href="./login.jsp"><button class="submit-button" type="button"> Login </button></a>
        <input id="signup-button" class="submit-button" type="submit" value="Signup" disabled>
        <div class="info">Provide Valid Response to turn this into Green to submit. 
            min password chars = 8 and password and confirm password should be same
        </div>
    </form>

    
</body>
</html>