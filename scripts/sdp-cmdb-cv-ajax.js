import { displayJSONData } from "./spd-cmdb-data-displayer.js";

let isProcessing = false;
const apikeytag = document.getElementById("api-key");


export function callAjax() {

    if (isProcessing) {
        console.warn("Processing Request ..");
        return;
    }
    
    isProcessing = true;
    let fields = getFields();

    C_V_AjaxPromise(apikeytag.value, fields).then((response)=>{
        displayJSONData(JSON.parse(response));
    })
    .catch((error)=>{
        document.getElementById("response-status").innerHTML = error;
    })
    .finally(()=>{
        isProcessing = false;
    });
    
}

function C_V_AjaxPromise(apikey, fields) {
    return new Promise((resolve, reject) => {
        var xhttp = new XMLHttpRequest();

        xhttp.open("POST", "./SDP-CMDB-CV-Assets");

        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === 4) {
                if (xhttp.status === 200) {
                    resolve(xhttp.responseText);
                }
                else{
                    reject("Ajax Side Error : XMLHTTP Status : "+ xhttp.status + " and Status Text : " + xhttp.statusText);
                }
            }
        }
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhttp.send(`apikey=${encodeURIComponent(apikey)}&fields=${encodeURIComponent(fields)}`);
    });
}

function getFields() {
    let options = document.getElementById("myFilter").style.getPropertyValue("--multitext");

    options = options.replaceAll("'","")

    if (options.split(",").length < 2) {
        return "CI Name,Asset State,Business Impact";
    }
    return options;
}