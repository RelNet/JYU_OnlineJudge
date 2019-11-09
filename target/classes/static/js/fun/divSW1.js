var ds = document.getElementById("hide2");
var hide2 = false;
function divSW1() {
    if(hide2){
        ds.style.display ="none";
        hide2 = false;
    }
    else{
        ds.style.display = "block";
        hide2 = true;
    }
}