import { callAjax } from "./sdp-cmdb-cv-ajax.js";
import { loadJSON } from "./JSONLoader.js";

$(function (){

    $('#fetch-button').on( "click", function(){
        callAjax();
    });

    
    const apikey_bar = $('#api-key');
    const fetch_button = $('#fetch-button');
    // By default,
    fetch_button.attr("disabled", "disabled");

    apikey_bar.on("input", function(){
        if (apikey_bar.val().length > 0) {
            fetch_button.removeAttr("disabled");
        } else {
            fetch_button.attr("disabled", "disabled");
        }
    });


    loadFields()
    .then(()=>{
        $(".multiple_select").mousedown(function(e) {
            if (e.target.tagName == "OPTION") 
            {
            return; //don't close dropdown if i select option
            }
            $(this).toggleClass('multiple_select_active'); //close dropdown if click inside <select> box
        });
        $(".multiple_select").on('blur', function(e) {
            $(this).removeClass('multiple_select_active'); //close dropdown if click outside <select>
        });
            
        $('.multiple_select option').mousedown(function(e) { //no ctrl to select multiple
            e.preventDefault(); 
            $(this).prop('selected', $(this).prop('selected') ? false : true); //set selected options on click
            $(this).parent().change(); //trigger change event
        });
        
            
        $("#myFilter").on('change', function() {
            var selected = $("#myFilter").val().toString(); //here I get all options and convert to string
            var document_style = document.getElementById("myFilter").style;
            if(selected !== "")
            document_style.setProperty('--multitext', "'"+selected+"'");
            else
            document_style.setProperty('--multitext', "'Select values'");
        });
    })
    .catch(()=>{
        console.error("Fields Not Loaded");
    })
});

async function loadFields() {
    const select_tag = document.getElementById("myFilter");

    await loadJSON("./assets/CI_fields.json").then((responseJSON)=>{
        responseJSON.fields.forEach(field => {
            let opt_tag = document.createElement("option");
            opt_tag.setAttribute("value", field);
            opt_tag.innerHTML = field;
            select_tag.appendChild(opt_tag);
        });
        
    })
    .catch((error)=>{
        console.error(error);
    })

}