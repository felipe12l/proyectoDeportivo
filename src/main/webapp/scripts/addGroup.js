let allGroups = []
let availableGroups = []
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
                return
            }
        }
        getGroups(disciplineSelect.value)
        resetLeaderboard()
    })

    getGroups(disciplineSelect.value)

    // Add sortable functionality to leaderboard table
    const leaderboardTbody = document.getElementById('leaderBoard').querySelector('tbody')
    new Sortable(leaderboardTbody, {
        animation: 150,
        onEnd: function (evt) {
            const item = leaderboard.splice(evt.oldIndex, 1)[0]
            leaderboard.splice(evt.newIndex, 0, item)
            showLeaderboard()
        }
    })
})

function getGroups(discipline) {
    const xhr = new XMLHttpRequest()
    const params = new URLSearchParams({ param: 2, discipline: discipline })

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true)
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const groups = JSON.parse(xhr.responseText)
                    allGroups = groups
                    availableGroups = groups
                    showGroups()
                } catch (e) {
                    console.error('Error parsing JSON:', e)
                    showNotification('Error parsing groups data.', 'error')
                }
            } else {
                console.error('Error fetching groups:', xhr.status, xhr.statusText)
            }
        }
    }

    xhr.onerror = () => {
        console.error('Request failed')
    }
    xhr.send()
}

function addGroup(groupName) {
    if (groupName) {
        availableGroups = availableGroups.filter(group => group.name !== groupName)
        leaderboard.push({ name: groupName, participants: [] })

        showGroups()
        showLeaderboard()
        showNotification('Team added successfully!', 'success')
    } else {
        showNotification('Group name is required', 'error')
    }
}

function showGroups() {
    const table = document.getElementById('tableToAdd').querySelector('tbody')
    table.innerHTML = ''
    availableGroups.forEach(group => {
        const row = document.createElement('tr')
        row.innerHTML = `
            <td>${group.name}</td>
            <td><i class="fas fa-eye" title="View participants" onclick="showParticipantsModal('${group.name}')"></i></td>
            <td><i class="fas fa-plus" title="Add" onclick="addGroup('${group.name}')"></i></td>
        `
        table.appendChild(row)
    })
}

function showLeaderboard() {
    const leaderboardT = document.getElementById('leaderBoard').querySelector('tbody')
    leaderboardT.innerHTML = ''
    leaderboard.forEach((group, index) => {
        const row = document.createElement('tr')
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${group.name}</td>
            <td><i class="fas fa-eye" title="View participants" onclick="showParticipantsModal('${group.name}')"></i></td>
            <td><i class="fas fa-minus" title="Remove" onclick="removeGroup('${group.name}')"></i></td>
        `
        leaderboardT.appendChild(row)
    })
}

function showParticipantsModal(groupName) {
    const group = allGroups.find(g => g.name === groupName)
    if (group) {
        const modalBody = document.getElementById('participantsModalBody')
        modalBody.innerHTML = ''
        group.participants.forEach(participant => {
            const row = document.createElement('tr')
            row.innerHTML = `
                <td>${participant.id}</td>
                <td>${participant.name}</td>
            `
            modalBody.appendChild(row)
        })

        const modal = new bootstrap.Modal(document.getElementById('participantsModal'))
        modal.show()
    }
}

function removeGroup(groupName) {
    leaderboard = leaderboard.filter(group => group.name !== groupName)
    availableGroups.push({ name: groupName, participants: [] })
    showGroups()
    showLeaderboard()
    showNotification('Team removed successfully!', 'success')
}

function resetLeaderboard() {
    leaderboard = []
    const leaderboardT = document.getElementById("leaderboard").querySelector('tbody')
    leaderboardT.innerHTML = ''
}

function back() {
    window.location.href = "main.html"
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
    const name = document.getElementById('reg_name').value
    const date = document.getElementById('reg_date').value

    if (!name || !date) {
        showNotification('All fields are required!', 'error')
        return
    }

    if (leaderboard.length === 0) {
        showNotification('Leaderboard must have at least one participant!', 'error')
        return
    }

    doPost()
    showNotification('Competition added successfully!', 'success')
}

function doPost() {
    const params = new URLSearchParams({ param: 2 })
    const name = document.getElementById('reg_name').value
    const date = document.getElementById('reg_date').value
    const disciplineSelect = document.getElementById('disciplineSelect').value
    const leaderboardJSON = JSON.stringify(leaderboard)

    const xhr = new XMLHttpRequest()
    xhr.open("POST", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true)
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4 && xhr.status === 200) {
            back()
        }
    }

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    const data = `name=${encodeURIComponent(name)}&date=${encodeURIComponent(date)}&discipline=${encodeURIComponent(disciplineSelect)}&leaderboard=${encodeURIComponent(leaderboardJSON)}`
    xhr.send(data)
}
