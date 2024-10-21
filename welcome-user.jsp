<%@page contentType="text/html"%>
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

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <link rel="stylesheet" href="./styles/index.css">
</head>
<body>
    <div class="form-field">
        <h2 style="margin-bottom: 1.5em;">List of Applications</h2>
        <a href="./get-api-key.jsp"><button type="button" class="submit-button"> LLM API Integration </button></a>
        <a href="./CMDB-CV-Assets.jsp"><button type="button" class="submit-button"> CMDB API Integration </button></a>
        <a href="./logout"><button type="button" class="submit-button"> Logout </button></a>
    </div>
</body>
</html>