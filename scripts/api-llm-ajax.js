import { formatGPT, logFormater } from "./formatGPT.js";

const url = './logprompt';

export function DataEventHandler(msg){
    const desc = $('#api-response-description');
    const rem = $('#api-response-remedy');
    const prev = $('#api-response-prevention');
    const ref = $('#api-response-references');
    let user_prompt = "I kindly request you to give me a 4 passage style response for the below double quoted Windows Log message, each paragraph should end with \"#PARA#\". First paragraph should have a 20 word description of the log message, Second paragraph should have the remedy for the issue, Third paragraph should have prevention of such issue, Fourth paragraph should have a reference where a software developer could get a help from online. Here is the Windows Log message in double quotes\"\"";
    let msgboxes = [desc, rem, prev, ref];

    AjaxPromise( user_prompt +msg.innerHTML+"\"\"", msgboxes).then((reply)=>{
        let replys = logFormater(reply);
        // If Server side Exception is thrown, the replys array contains only one element.
        if (replys.length === 1) {
            msgboxes.forEach((box) => {
                box.html(formatGPT(replys[0]));
            });
        }
        // Else its a normal reply. 
        else {
            replys.forEach((reply, index) => {
                msgboxes[index].html(formatGPT(reply));
            });
        }
        
        
    }).catch((error)=>{
        console.error(error);
    });
};

function AjaxPromise(prompt, msgboxes) {
    return new Promise((resolve, reject) => {

        msgboxes.forEach((box) => {
            box.html(' ... Please wait while loading your Respose ...');
        });

        let model = document.getElementById("gpt-model-select").value;

        var xhttp = new XMLHttpRequest();

        xhttp.open("POST", url);
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhttp.onreadystatechange = function(){

            if (xhttp.readyState === 4) {
                if (xhttp.status === 200) {
                    resolve(xhttp.responseText);
                }
                else{
                    reject("!! Http Status : "+ xhttp.status + " and Status Text : " + xhttp.statusText);
                }
            }
        }

        xhttp.onerror = function(){
            reject(xhttp.statusText);
        }
        

        
        xhttp.send(`prompt=${encodeURIComponent(prompt)}&gptmodel=${encodeURIComponent(model)}`)

    });
}