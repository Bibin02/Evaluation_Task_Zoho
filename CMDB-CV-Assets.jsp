<%@ page contentType="html" %>
<% 
    String sessionAttr = (String)session.getAttribute("login");

    if(sessionAttr != null){
        if(sessionAttr.equals("no")){
            session.setAttribute("msg", "Kindly Login ...");
            response.sendRedirect("./login.jsp");
        }
        // Enter into this Page.
    }
    else{
        session.setAttribute("msg", "Session Expired Kindly Login Again ...");
        response.sendRedirect("./login.jsp");
    }

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CMDB Critical and Vulnerable Assets</title>
    <!-- Style Sheet -->
    <link rel="stylesheet" href="./styles/CMDB.css">
    <!-- JavaScript -->
    <script type="module" src="./scripts/jquery-3.7.1.js"></script>
    <script type="module" src="./scripts/cmdb-cv-assets.js"></script>
    <script type="module" src="./scripts/cmdb-cv-ajax.js"></script>
    <script type="module" src="./scripts/cmdb-data-displayer.js"></script>
    
</head>
<body>
    <div class="container">
        <div class="options-container">
            <select id="cmdb-selector" class="cmdb-selector-class">
                <!-- Load Data From JSON -->
            </select>

            <input class="input-key" id="api-key" type="password" placeholder="Enter API Key">

            <input class="input-key" id="object-id" type="text" placeholder="Object ID">

            <select id="myFilter" class="multiple_select" style="--multitext: 'Select values';" multiple>
                <!-- Load Data From JSON -->
              </select>
            <button id="fetch-button" > Fetch Data </button>
            <a href="./welcome-user.jsp" style="text-decoration: none;" class="logout-button"> Back </a>
        </div>
        <div id="response-status" class="response-status-ajax"></div>
        <div class="log-table-container">
            <table class="log-table">
                <tbody id="log-table-body" class="log-table-body">
                    <!-- Loaded Headers and TDs -->

                </tbody>
            </table>
        </div>
        
    </div>
</body>
</html>
