import { loadJSON } from "./JSONLoader.js";

const select_tag = document.getElementById("gpt-model-select");
const gpt_provider = document.getElementById("gpt-provider").innerHTML;

let jsonResponse = loadJSON("./assets/models.json");

jsonResponse.then((jsonData)=>{

    jsonData.data.forEach(object => {
        if (object.provider.trim() === gpt_provider.trim()) {
            object.data.forEach(model => {
                let opt_tag = document.createElement("option");
                opt_tag.setAttribute("value", model.id);
                opt_tag.innerHTML = model.id;
                select_tag.appendChild(opt_tag);

            })
        }
    });
})
.catch((error)=>{
    console.error(error);
});