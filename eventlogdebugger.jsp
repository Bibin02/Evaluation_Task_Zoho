<%@ page contentType="html" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open AI Platfrom</title>
    <link rel="stylesheet" href="./styles/debugger-page.css">
    <script
        type="module"
        src="https://code.jquery.com/jquery-3.7.1.js"
        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous">
    </script>
    <script type="module" src="./scripts/promptLoader.js"></script>
    <script type="module" src="./scripts/formatGPT.js"></script>
    <script type="module" src="./scripts/eventlogdebugger.js"></script>
    <script type="module" src="./scripts/api-llm-ajax.js"></script>
    
</head>
<body>

    <% 
        String sessionAttr = (String)session.getAttribute("login");

        if(sessionAttr != null){
            if(sessionAttr.equals("no") || session.getAttribute("apikey") == null){
                session.setAttribute("msg", "Kindly Login ...");
                response.sendRedirect("./index.jsp");
            }
            // Enter into this Page.
        }
        else{
            session.setAttribute("msg", "Session Expired Kindly Login Again ...");
            response.sendRedirect("./index.jsp");
        }

    %>
    <div class="container">
        <div class="options-container">
            <div id="gpt-provider"><%= (String)session.getAttribute("url") %></div>
            <select id="gpt-model-select" name="gpt-model">
                <!-- Loaded Dynamically -->
            </select>
            <a href="logout" style="text-decoration: none;" class="logout-button">Logout</a>
        </div>

        <div class="log-table-container">
            <table class="log-table">
                <tbody id="log-table-body" class="log-table-body">
                    <tr class="log-table-header">
                         <th colspan="8">System</th>
                         <th colspan="3">RenderingInfo</th>
                    </tr>
                    <tr class="log-table-header">
                         <th>EventID</th>
                         <th>Level</th>
                         <th>Task</th>
                         <th>Keywords</th>
                         <th>TimeCreated</th>
                         <th>Channel</th>
                         <th>Computer</th>
                         <th>Security</th>

                         <th>Message</th>
                         <th>Level</th>
                         <th>Opcode</th>
                    </tr>
                    
                    <!-- Load Row Data from json -->

                </tbody>
            </table>
        </div>

        <div class="log-description-box">
            <table class="description-table">
                <tbody>
                    <tr class="table-header">
                        <th>Description</th> <th>Remedy</th> <th>Prevention</th> <th>References</th>
                    </tr>

                    <tr class="table-body-row">
                        <!-- API Response for Log Report by LLM -->
                         <!-- Description -->
                        <td id="api-response-description" class="api-response-box"> </td>
                        <!-- Remedy -->
                        <td id="api-response-remedy" class="api-response-box"> </td>
                        <!-- Prevention -->
                        <td id="api-response-prevention" class="api-response-box"> </td>
                        <!-- References -->
                        <td id="api-response-references" class="api-response-box"> </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
    </div>
</body>
</html>
