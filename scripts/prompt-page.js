import { formatGPT } from "./formatGPT.js";

$(document).ready(()=>{

    let prompt_question = $(".prompt-question");

    $('.prompt-reply').html(formatGPT($('.prompt-reply').text()));

    // Check if there's any saved prompt data in local storage
    let storedPrompts = JSON.parse(localStorage.getItem('storedPrompts')) || [];

    // If the prompt text is not 'Prompt', proceed
    if (!(prompt_question.text() === 'Prompt')) {
        let chatbox = document.getElementById("chats-container");

        let prompt_tab = document.createElement("div");
        prompt_tab.setAttribute("class", "prompt-tab");

        let lastChild = chatbox.lastElementChild;
        if (lastChild === null) {
            prompt_tab.setAttribute("id", "prompt-id-1");
        } else {
            let lastChildId = lastChild.getAttribute("id");
            let prompt_id = Number(lastChildId.slice(10));
            prompt_tab.setAttribute("id", "prompt-id-" + (prompt_id + 1));
        }

        // Get the prompt text and store it in local storage
        let promptText = prompt_question.text();
        let promptResponse = $('.prompt-reply').html();
        prompt_tab.innerHTML = promptText.slice(0, 40) + "....";

        if (promptResponse.slice(0,13) !== '<strong style'){
            if (storedPrompts.length > 0){
                if (storedPrompts[storedPrompts.length-2] !== promptText) {
                    // Save the current prompt to local storage
                    storedPrompts.push(promptText);
                    storedPrompts.push(promptResponse);
                    localStorage.setItem('storedPrompts', JSON.stringify(storedPrompts));
                }
            }
            else{
                // Save the current prompt to local storage
                storedPrompts.push(promptText);
                storedPrompts.push(promptResponse);
                localStorage.setItem('storedPrompts', JSON.stringify(storedPrompts));
            }
        }
        
    }

    // Load saved prompts from local storage on page reload
    if (storedPrompts.length > 0) {
        let chatbox = document.getElementById("chats-container");

        for (let i = 0; i < storedPrompts.length; i+=2) {
            const savedPrompt = storedPrompts[i];
            const savedResponse = storedPrompts[i+1];

            let prompt_tab = document.createElement("div");
            prompt_tab.setAttribute("class", "prompt-tab");
            prompt_tab.setAttribute("id", "prompt-id-" + ((i/2)+1));
            prompt_tab.innerHTML = savedPrompt;

            // Add event listener for clicking the prompt tab
            prompt_tab.addEventListener("click", function() {
                alert(this.innerHTML);
                $('.prompt-question').text(savedPrompt);
                $('.prompt-reply').html(savedResponse);
                $('#prompt').text('');
            });
            chatbox.appendChild(prompt_tab);
        }
        
        
    }
    

    var prompt = $('#prompt');
    var prompt_inp = $('input[value="Prompt"]');

    prompt_inp.attr("disabled", "true");
    
    prompt_inp.click(()=>{
        alert("You have been Prompted.");
    })

    $('input[value="Logout"]').click(()=>{
        localStorage.clear();
    })

    prompt.on("input", ()=>{
        let ptext = document.getElementById("prompt").value;

        if(ptext.length > 0){
            prompt_inp.removeAttr("disabled");
        }
        else{
            prompt_inp.attr("disabled", "true");
        }
    })
})
