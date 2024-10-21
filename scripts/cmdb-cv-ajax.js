import { displayJSONData } from "./cmdb-data-displayer.js";

let isProcessing = false;
const cmdb_select = document.getElementById("cmdb-selector");
const apikeytag = document.getElementById("api-key");
const object_id = document.getElementById("object-id");


export function callAjax() {

    if (isProcessing) {
        console.warn("Processing Request ..");
        return;
    }
    
    isProcessing = true;
    let fields = getFields();

    C_V_AjaxPromise().then((response)=>{
        displayJSONData(JSON.parse(response));
    })
    .catch((error)=>{
        document.getElementById("response-status").innerHTML = error;
    })
    .finally(()=>{
        isProcessing = false;
    });
    
}

function C_V_AjaxPromise() {
    return new Promise((resolve, reject) => {
        var xhttp = new XMLHttpRequest();

        xhttp.open("POST", "./CMDB-CV-Assets");

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
        xhttp.setRequestHeader("Content-Type", "application/json");

        let requestJSON = 
        {
            cmdb: cmdb_select.value,
            object_id: object_id.value,
            apikey: apikeytag.value,
            fields: getFields(),
        };

        xhttp.send(JSON.stringify(requestJSON));
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