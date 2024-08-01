let participantsToAdd = [];
let participantsAdded = [];

document.addEventListener('DOMContentLoaded', function () {
    getParticipants();
});

function addParticipant() {
    const select = document.getElementById('userSelect');
    const participant = select.options[select.selectedIndex].text;

    if (participant && !participantsToAdd.includes(participant)) {
        participantsToAdd.push(participant);
        showParticipantsToAdd();
    }
}

function showParticipantsToAdd() {
    const table = document.getElementById('participants-to-add');
    table.innerHTML = `<table class="table table-bordered"><thead><tr><th>Participant</th><th>Action</th></tr></thead><tbody>`;
    participantsToAdd.forEach((participant, index) => {
        table.innerHTML += `<tr><td>${participant}</td><td><button class="btn btn-success" onclick="addToParticipantsAdded(${index})">Add</button></td></tr>`;
    });
    table.innerHTML += `</tbody></table>`;
}

function showParticipantsAdded() {
    const table = document.getElementById('participants-added');
    table.innerHTML = `<table class="table table-bordered"><thead><tr><th>Participant</th><th>Action</th></tr></thead><tbody>`;
    participantsAdded.forEach((participant, index) => {
        table.innerHTML += `<tr><td>${participant}</td><td><button class="btn btn-danger" onclick="removeFromParticipantsAdded(${index})">Remove</button></td></tr>`;
    });
    table.innerHTML += `</tbody></table>`;
}

function addToParticipantsAdded(index) {
    const participant = participantsToAdd.splice(index, 1)[0];
    participantsAdded.push(participant);
    showParticipantsToAdd();
    showParticipantsAdded();
}

function removeFromParticipantsAdded(index) {
    const participant = participantsAdded.splice(index, 1)[0];
    participantsToAdd.push(participant);
    showParticipantsToAdd();
    showParticipantsAdded();
}

function addCompetition() {
    // Aquí iría la lógica para añadir la competición
    alert('Competition added!');
}

function back() {
    window.location.href = "main.html";
}

function getParticipants() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/proyectoDeportivo_war_exploded/club-servlet", true);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const users = JSON.parse(xhr.responseText);
                    const select = document.getElementById('userSelect');
                    select.innerHTML = '';  // Limpiar opciones anteriores
                    users.forEach(user => {
                        const option = document.createElement('option');
                        option.value = user.id;  // Asumiendo que cada usuario tiene una ID única
                        option.text = user.name; // Asumiendo que cada usuario tiene un nombre
                        select.add(option);
                    });
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                }
            } else {
                console.error('Error fetching participants:', xhr.status, xhr.statusText);
            }
        }
    };
    xhr.onerror = () => {
        console.error('Request failed');
    };
    xhr.send();
}