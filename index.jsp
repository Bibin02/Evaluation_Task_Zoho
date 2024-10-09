<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zoho Eval Webpage</title>
    <link rel="stylesheet" href="./styles/index.css">
</head>
<body>

    <%
        if(session.getAttribute("msg") != null){
            session.setAttribute("msg", "");
        }
    %>

    <h2 style="text-align: center;">Welcome to My Zoho Evaluation Tasks</h2>
    <div class="redirect-container form-field">
        <a href="./login.jsp"><button class="submit-button" type="button">Login</button></a>
        <a href="./signup.jsp"><button class="submit-button" type="button">Signup</button></a>
    </div>
    
</body>
</html>