let participantsToAdd = []
let leaderboard = []
let previousDiscipline = ''

document.addEventListener('DOMContentLoaded', function () {
    const disciplineSelect = document.getElementById('disciplineSelect')

    previousDiscipline = disciplineSelect.value

    disciplineSelect.addEventListener('change', function () {
        if (leaderboard.length > 0) {
            const confirmChange = confirm("Changing the discipline will discard the current leaderboard data. Do you want to continue?")
            if (!confirmChange) {
                disciplineSelect.value = previousDiscipline
            }
        }
        getParticipants(disciplineSelect.value)
        resetLeaderboard()
    });
    getParticipants(disciplineSelect.value)

    // Inicializar Sortable en el leaderboard
    new Sortable(document.getElementById('leaderboard-body'), {
        animation: 150,
        onEnd: function (/**Event*/evt) {
            reorderLeaderboard(evt.oldIndex, evt.newIndex)
        }
    });
});

function reorderLeaderboard(oldIndex, newIndex) {
    const movedItem = leaderboard.splice(oldIndex, 1)[0]
    leaderboard.splice(newIndex, 0, movedItem)
    showLeaderboard()
}


function showParticipantsToAdd(users) {
    const table = document.getElementById('tableToAdd').querySelector('tbody')
    table.innerHTML = ''
    users.forEach(user => {
        const row = document.createElement('tr')
        row.innerHTML = `<td>${user.id}</td><td>${user.name}</td><td><i class="fas fa-plus" title="Add" onclick="addParticipant(${user.id})"></i></td>`
        table.appendChild(row)
    });
}

function showLeaderboard() {
    const leaderboardT = document.getElementById('leaderboard').querySelector('tbody')
    leaderboardT.innerHTML = ''
    leaderboard.forEach((participant, index) => {
        const row = document.createElement('tr')
        row.innerHTML = `<td>${index + 1}</td><td>${participant.id}</td><td>${participant.name}</td><td><i class="fas fa-minus" title="Remove" onclick="removeParticipant(${index})"></i></td>`;
        leaderboardT.appendChild(row)
    })
}

function addParticipant(userId) {
    const user = participantsToAdd.find(participant => participant.id === userId)
    if (user) {
        leaderboard.push(user)
        participantsToAdd = participantsToAdd.filter(participant => participant.id !== userId)
        showParticipantsToAdd(participantsToAdd)
        showLeaderboard()
        showNotification('Participant added successfully!', 'success')
    } else {
        showNotification('Participant not found!', 'error')
    }
}

function removeParticipant(index) {
    const participant = leaderboard.splice(index, 1)[0]
    participantsToAdd.push(participant)
    showParticipantsToAdd(participantsToAdd)
    showLeaderboard()
    showNotification('Participant removed successfully!', 'success')
}



function back() {
    window.location.href = "main.html"
}

function getParticipants(discipline) {
    const xhr = new XMLHttpRequest()
    const params = new URLSearchParams({ param: 1, discipline: discipline })

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true)
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const users = JSON.parse(xhr.responseText)
                    participantsToAdd = users
                    showParticipantsToAdd(users)
                } catch (e) {
                    console.error('Error parsing JSON:', e)
                    showNotification('Error parsing participants data.', 'error')
                }
            } else {
                console.error('Error fetching participants:', xhr.status, xhr.statusText)
            }
        }
    }


    xhr.onerror = () => {
        console.error('Request failed')
    }
    xhr.send()
}

function resetLeaderboard(){
    leaderboard = []
    const leaderboardT = document.getElementById("leaderboard").querySelector('tbody')
    leaderboardT.innerHTML = ''

}

function showNotification(message, type) {
    const notification = document.getElementById('notification')
    notification.innerText = message
    notification.style.backgroundColor = type === 'success' ? '#28a745' : '#dc3545'
    notification.style.display = 'block'
    setTimeout(() => {
        notification.style.display = 'none'
    }, 3000)
}

function addCompetition() {

    const name = document.getElementById('reg_name').value;
    const date = document.getElementById('reg_date').value;

    if (!name || !date) {
        showNotification('All fields are required!', 'error');
        return;
    }

    if (leaderboard.length === 0) {
        showNotification('Leaderboard must have at least one participant!', 'error');
        return;
    }

    doPost()
    showNotification('Competition added successfully!', 'success');

}

function doPost(){
    const params = new URLSearchParams({ param: 1})
    const name = document.getElementById('reg_name').value;
    const date = document.getElementById('reg_date').value;
    const place = document.getElementById('reg_place').value;
    const disciplineSelect = document.getElementById('disciplineSelect').value;
    const leaderboardJSON = JSON.stringify(leaderboard);

    const xhr = new XMLHttpRequest()
    xhr.open("POST", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true)
    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4 && xhr.status === 200){
            back()
        }
    }

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    const data = `name=${encodeURIComponent(name)}&date=${encodeURIComponent(date)}&place=${encodeURIComponent(place)}&discipline=${encodeURIComponent(disciplineSelect)}&leaderboard=${encodeURIComponent(leaderboardJSON)}`;
    console.log(data)
    xhr.send(data)
}

