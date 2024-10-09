export function displayJSONData(responseJSON) {

    const response_bar = document.getElementById("response-status");
    const table_body = document.getElementById("log-table-body");
    // Delete Past Nodes
    table_body.replaceChildren();

    if (responseJSON.message != null) {
        response_bar.innerHTML = responseJSON.message;
        return;
    }

    let operationData = responseJSON.API.response.operation.Details;
    let headerArray = operationData["field-names"].name;
    let recordsArray = operationData["field-values"].record;
    response_bar.innerHTML = "Fetch Success";

    // Append Headers
    let tableRow = document.createElement("tr");
    tableRow.setAttribute("class", "log-table-header");
    headerArray.forEach(header => {
        let tableCol = document.createElement("th");
        tableCol.innerHTML = header.content;
        tableRow.appendChild(tableCol);
    });
    table_body.appendChild(tableRow);


    // Appending Records
    recordsArray.forEach(record => {
        tableRow = document.createElement("tr");
        record.value.forEach(item =>{
            let tableCol = document.createElement("td");
            tableCol.innerHTML = item;
            tableRow.appendChild(tableCol);
            if (item === "High") {
                tableCol.parentElement.style.fontWeight = "1000";
            }
        });
        table_body.appendChild(tableRow);
    });
    
    
}