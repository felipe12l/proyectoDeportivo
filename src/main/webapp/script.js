const general = document.getElementById("general")
const addT = document.getElementById("add-tournament-form")
const filterSelect = document.getElementById("filterSelect");
const userSelect = document.getElementById("userSelect");

filterSelect.value = "c"
filterSelect.addEventListener("change", function() {
    const selectedValue = filterSelect.value;

    if (selectedValue === "c") {
        document.getElementById("info-c").style.display = "block"; // O mostrar la sección de torneos
        document.getElementById("info-u").style.display = "none"; // Asegúrate de tener una sección para usuarios
    } else if (selectedValue === "u") {
        document.getElementById("info-c").style.display = "none"; // Ocultar la sección de torneos
        document.getElementById("info-u").style.display = "block"; // O mostrar la sección de usuarios
    }
});

userSelect.addEventListener("change", function() {
    const selectedValue = userSelect.value;

});


function newGroup(){


}

function addTournament(){

}

function showAdd(){
    const name = document.getElementById("reg_name")
    addT.style.display = "block"
    general.style.display = "none"
}
function showSee(){

}
function back(){
    addT.style.display = "none"
    general.style.display = "block"
}