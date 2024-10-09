<%@page contentType="text/html" import="java.util.*" import="database.*"%>
<%
    String sessionAttr = (String)session.getAttribute("login");

    if(sessionAttr != null){
        if(sessionAttr.equals("yes") && session.getAttribute("apikey") != null){
            // Enter into this Page.
        }
        else if (sessionAttr.equals("no")){
            response.sendRedirect("./login.jsp");
        }
        else {
            response.sendRedirect("./get-api-key.jsp");
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
    <title>My LLM Applications</title>
    <link rel="stylesheet" href="./styles/llm-home.css">
</head>
<body>
    <h4 class="log-msg"><% out.println((String)session.getAttribute("msg")); %></h4>
    <div class="flex-box-tasks">
        <div class="flex-task">
            <div class="task-desc">
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Animi fugit dolorem eaque tempore debitis. 
                Ea quibusdam reiciendis deleniti dolore, fuga facilis nostrum et reprehenderit error ratione est, 
                sequi vitae saepe.
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Eligendi, commodi aliquid accusantium reiciendis 
                libero reprehenderit fuga natus eum eos aut necessitatibus minima ratione! Sint quisquam minus laborum porro ipsam minima.
            </div>
            <a href="./open-ai-prompt.jsp"><button type="button" class="submit-button"> Simple LLM Prompt </button></a>
        </div>
        <div class="flex-task">
            <div class="task-desc">
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Animi fugit dolorem eaque tempore debitis. 
                Ea quibusdam reiciendis deleniti dolore, fuga facilis nostrum et reprehenderit error ratione est, 
                sequi vitae saepe.
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Sed, voluptatem officia! Excepturi natus, dolorem dolorum ipsam, perferendis ducimus similique illum perspiciatis, eveniet animi quaerat accusantium illo quos a nam tempora.
                Quae magni exercitationem tempora, nam inventore fuga dolore delectus debitis nemo quos ab! Voluptas modi placeat eum ad aspernatur. Eveniet, necessitatibus! Soluta quo ipsa iste optio mollitia vel commodi similique?
                Voluptate nemo laudantium recusandae doloremque impedit adipisci fugiat! Aut saepe placeat tempore dolor, quis dolorum unde optio consequuntur cum veritatis numquam eligendi alias quidem iste? Voluptate debitis iure officiis alias.
                Odit recusandae officia quibusdam fugiat eaque maxime ratione exercitationem sit mollitia, in molestiae? Explicabo, quidem maiores? Similique eaque, modi temporibus ducimus, laboriosam repellat impedit ex unde, quidem nobis distinctio sequi!
                Rem et corporis consectetur, expedita perspiciatis recusandae. Magnam, est. Pariatur, vitae suscipit fugit consectetur doloremque facilis reiciendis assumenda quo quam natus excepturi nostrum non labore perspiciatis adipisci dolor minus autem.
            </div>
            <a href="./eventlogdebugger.jsp"><button type="button" class="submit-button"> Event Log Debugger </button></a>
        </div>
        <a href="./rem-apikey"><button type="button" class="submit-button back-button"> Back </button></a>
    </div>
    

</body>
</html>