const general = document.getElementById("general")
const addC = document.getElementById("add-competition")
const filterSelect = document.getElementById("filterSelect");
const userSelect = document.getElementById("userSelect");

filterSelect.value = "c"
filterSelect.addEventListener("change", function() {
    const selectedValue = filterSelect.value;
    if (selectedValue === "c") {
        document.getElementById("info-c").style.display = "block";
        document.getElementById("info-u").style.display = "none";
    } else if (selectedValue === "u") {
        document.getElementById("info-c").style.display = "none";
        document.getElementById("info-u").style.display = "block";
    }
});

function handleCompetitionType() {
    const competitionType = document.querySelector('input[name="competitionType"]:checked').value;
    if (competitionType === "group") {
        window.location.href = "addGroup.html"
    } else {
        window.location.href = "addIndividual.html"
    }
    const competitionTypeModal = bootstrap.Modal.getInstance(document.getElementById('competitionTypeModal'));
    competitionTypeModal.hide();
    showAdd();
}


function showAdd(){
    const name = document.getElementById("reg_name")
    addC.style.display = "block"
    general.style.display = "none"
}
function showSee(){

}
function back(){
    addC.style.display = "none"
    general.style.display = "block"
}