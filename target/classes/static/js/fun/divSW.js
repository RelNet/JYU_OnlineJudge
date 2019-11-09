var dv = document.getElementById("hide");
var hide = false;
function divSW() {
    if(hide){
        dv.style.display ="none";
        hide = false;
    }
    else{
        dv.style.display = "block";
        hide = true;
    }

}