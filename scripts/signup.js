$(function (){

    $($('#pass')).on( "input", function (){
        validatePassword();
    });

    $($('#cnfpass')).on( "input", function (){
        validatePassword();
    });

});

const passtag = document.getElementById("pass");
const cnfpasstag = document.getElementById("cnfpass");

function validatePassword() {
    let password = passtag.value;
    let cnfpassword = cnfpasstag.value;
    
    if (Number(password.length) >= 8 && cnfpassword === password) {
        alert("Password Validated.\n Thankyou for signing up.")
        document.getElementById("signup-button").removeAttribute("disabled");
    }
    else{
        document.getElementById("signup-button").setAttribute("disabled", "");
    }
}