let participantsToAdd = [];
let participantsAdded = [];

document.addEventListener('DOMContentLoaded', function () {
    getParticipants();
});



function showParticipantsToAdd(users) {
    const table = document.getElementById('tableToAdd').querySelector('tbody');
    table.innerHTML = '';
    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${user.id}</td><td>${user.name}</td><td><i class="fas fa-plus" title="Add" onclick="addParticipant(${user.id})"></i></td>`;
        table.appendChild(row);
    });
}

function showParticipantsAdded() {
    const table = document.getElementById('tableAdded').querySelector('tbody');
    table.innerHTML = '';
    participantsAdded.forEach((participant, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${participant.id}</td><td>${participant.name}</td><td><i class="fas fa-minus" title="Remove" onclick="removeParticipant(${index})"></i></td>`;
        table.appendChild(row);
    });
}

function addParticipant(userId) {

    const user = participantsToAdd.find(participant => participant.id === userId);
    if (user) {

        participantsAdded.push(user);
        participantsToAdd = participantsToAdd.filter(participant => participant.id !== userId);
        showParticipantsToAdd(participantsToAdd);
        showParticipantsAdded();
        showNotification('Participant added successfully!', 'success');
    } else {
        showNotification('Participant not found!', 'error');
    }
}

function removeParticipant(index) {
    const participant = participantsAdded.splice(index, 1)[0];
    participantsToAdd.push(participant);
    showParticipantsToAdd(participantsToAdd);
    showParticipantsAdded();
    showNotification('Participant removed successfully!', 'success');
}


function addCompetition() {
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
                    participantsToAdd = users;
                    showParticipantsToAdd(users);
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                    showNotification('Error parsing participants data.', 'error');
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


function showNotification(message, type) {
    const notification = document.getElementById('notification');
    notification.innerText = message;
    notification.style.backgroundColor = type === 'success' ? '#28a745' : '#dc3545';
    notification.style.display = 'block';
    setTimeout(() => {
        notification.style.display = 'none';
    }, 3000);
}
