<%@ page contentType="html" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open AI Platfrom</title>
    <link rel="stylesheet" href="./styles/prompt-page.css">
    
    <script type="module" src="./scripts/jquery-3.7.1.js"></script>
    <script type="module" src="./scripts/formatGPT.js"></script>
    <script type="module" src="./scripts/prompt-page.js"></script>
    <script type="module" src="./scripts/promptLoader.js"></script>
</head>
<body>

    <% 
        String sessionAttr = (String)session.getAttribute("login");

        if(sessionAttr != null){
            if(sessionAttr.equals("no") || session.getAttribute("apikey") == null){
                session.setAttribute("msg", "Kindly Login ...");
                response.sendRedirect("./get-api-key.jsp");
            }
            // Enter into this Page.
        }
        else{
            session.setAttribute("msg", "Session Expired Kindly Login Again ...");
            response.sendRedirect("./index.jsp");
        }

    %>
    <div class="container">

        <div id="chats-container" class="chats-container">

            <!-- Here comes the recent prompts. -->

        </div>

        <div class="chat-area">

            <div class="prompt-response">
                <%
                    if(request.getAttribute("pro") != null && request.getAttribute("res") != null){
                        out.println("<div class=\"prompt-question\">"+ request.getAttribute("pro").toString()+ "</div>");
                        out.println("<div class=\"prompt-reply\">"+ request.getAttribute("res").toString() +"</div>");
                    }
                    else{
                        out.println("<div class=\"prompt-question\">Prompt</div>");
                        out.println("<div class=\"prompt-reply\">Response</div>");
                    }
                    
                %>
            </div>

            <form class="prompt-form" action="prompt" method="post">

                <div class="gpt-provider-tab" id="gpt-provider">
                    <% 
                        String provider = (String)session.getAttribute("url");
                        out.println(provider);
                    %>
                </div>

                <select name="gpt-model" id="gpt-model-select">
                    <!-- Loaded Dynamically -->
                </select>

                <label for="prompt">
                    <input class="prompt-input" name="prompt" id="prompt" type="text" placeholder="prompt" required>
                </label>
                <input type="submit" value="Prompt">
            </form>
        </div>

        <form action="llm-home.jsp" method="post">
            <input type="submit" value="Back">
        </form>
    </div>
</body>
</html>
