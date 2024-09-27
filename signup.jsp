<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Open AI Signup</title>
    <link rel="stylesheet" href="./styles/index.css">
</head>
<body>

    <%
        String sessionAttr = (String)session.getAttribute("login");

        if(sessionAttr != null){
            if(sessionAttr.equals("yes")){
                response.sendRedirect("./get-api-key.jsp");
            }
            // Enter into this Page.
        }
        // Enter into this Page.

    %>
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
        <button class="submit-button" type="button" onclick="validatePassword()">Validate</button>
        <input id="signup-button" class="submit-button" type="submit" value="Signup" disabled>
    </form>

    <script>
        function validatePassword() {
            let passtag = document.getElementById("pass").value;
            let cnfpasstag = document.getElementById("cnfpass").value;
            
            if (passtag.length < 6) {
                alert("Password Should be atleast 6 letters / Numbers.")
            }
            else if (passtag !== cnfpasstag) {
                alert("Password and Confirm Password Doesn't Match.")
            }
            else{
                alert("Password Valid. Thankyou for signing up.")
                document.getElementById("signup-button").removeAttribute("disabled");
            }
        }
    </script>
</body>
</html>