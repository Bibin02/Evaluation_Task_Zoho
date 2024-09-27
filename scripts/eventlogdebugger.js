import { DataEventHandler } from "./api-llm-ajax.js";
import { loadJSON } from "./JSONLoader.js";

const tableBody = document.getElementById("log-table-body");

let jsonResponse = loadJSON("./assets/applog.json");

jsonResponse.then((jsonData)=>{
    
    jsonData.Events.Event.forEach(function (object) {
        
        let tableRow = document.createElement("tr");
        tableRow.setAttribute("class", "log-table-row");

        let tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.EventID;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Level;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Task;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Keywords;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.TimeCreated;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Channel;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Computer;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Security;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col-main");
        tableCol.innerHTML = object.RenderingInfo.Message;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Level;
        tableRow.appendChild(tableCol);

        tableCol = document.createElement("td");
        tableCol.setAttribute("class", "log-table-col");
        tableCol.innerHTML = object.System.Opcode;
        tableRow.appendChild(tableCol);

        tableBody.appendChild(tableRow);
    });
})
.catch((error)=>{
    console.error(error);
});

$(document).on('click', '.log-table-col-main', function(){
    DataEventHandler(this);
});