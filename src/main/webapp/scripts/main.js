const general = document.getElementById("general")
const userSelect = document.getElementById("userSelect");
const infoC = document.getElementById("info-c");
const infoU = document.getElementById("info-u");



let allUsers = []
let allCompetitions = []

const filterInfo = document.getElementById("filterInfo");
filterInfo.value = 'c'

filterChanged()

function filterChanged() {
    const tableContainer = document.getElementById('tableContainer');
    tableContainer.innerHTML = '';

    if (filterInfo.value === 'c') {
        tableContainer.innerHTML = `
            <table class="table table-bordered" id="info-c">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Discipline</th>
                    <th style="max-width: 50px">Leaderboard</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        `;
        getCommpetitions();
    } else if (filterInfo.value === 'u') {
        tableContainer.innerHTML = `
            <table class="table table-bordered" id="info-u">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Discipline</th>
                    <th style="width: 50px">Competitions Count</th>
                    <th style="width: 50px">Action</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        `;
        getUsers();
    }
}


function handleCompetitionType() {
    const competitionType = document.querySelector('input[name="competitionType"]:checked').value;
    if (competitionType === "group") {
        window.location.href = "addGroup.html"
    } else {
        window.location.href = "addIndividual.html"
    }
    const competitionTypeModal = bootstrap.Modal.getInstance(document.getElementById('competitionTypeModal'));
    competitionTypeModal.hide();
}

function getCommpetitions() {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({ param: 2 });


    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    if (xhr.responseText) {
                        const competitions = JSON.parse(xhr.responseText);
                        allCompetitions = competitions;
                        console.log(allCompetitions)
                        showInfoCompetition();
                        showNotification('Data retrieved.', 'success');

                    } else {
                        showNotification('Empty response received.', 'error');
                        console.error('Empty response received.');
                    }
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                    console.error('Response:', xhr.responseText);
                    showNotification('Error parsing users data.', 'error');
                }
            } else {
                console.error('Error fetching users:', xhr.status, xhr.statusText);
            }
        }
    };

    xhr.onerror = () => {
        console.error('Request failed');
    };
    xhr.send();
}


function showInfoCompetition() {
    const tbody = document.getElementById('info-c').querySelector('tbody');
    tbody.innerHTML = '';

    allCompetitions.forEach(cmp => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${cmp.name}</td>
            <td>${cmp.date}</td>
            <td>${cmp.discipline.name}</td>
            <td>
                <i class="fas fa-eye" title="See" onclick="viewLeaderboard('${cmp.name}')"></i>
            </td>
        `;

        tbody.appendChild(row);
    });
}


function viewLeaderboard(name) {
    const competition = allCompetitions.find(cmp => cmp.name === name);

    if (competition) {
        document.getElementById('competitionName').innerText = competition.name;
        document.getElementById('competitionDate').innerText = competition.date;
        document.getElementById('competitionDiscipline').innerText = competition.discipline.name;

        const leaderboardList = document.getElementById('leaderboardList');
        leaderboardList.innerHTML = '';

        let leaderboardHTML = `
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Puesto</th>
                        <th>Nombre</th>
                    </tr>
                </thead>
                <tbody>
        `;
        console.log(competition.discipline.isGroup.value)
        if (competition.discipline.isGroup) {
            // Display teams in leaderboard
            competition.leaderboard.forEach((team, index) => {
                leaderboardHTML += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>Team: ${team.name} - Participants: ${team.participants.map(p => p.name).join(', ')}</td>
                    </tr>
                `;
            });
        } else if (!competition.discipline.isGroup) {
            // Display users in leaderboard
            competition.leaderboard.forEach((user, index) => {
                leaderboardHTML += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>User: ${user.name} ${user.lastName} - Age: ${user.age}</td>
                    </tr>
                `;
            });
        }

        leaderboardHTML += `
                </tbody>
            </table>
        `;

        leaderboardList.innerHTML = leaderboardHTML;

        // Show the modal
        const leaderboardModal = new bootstrap.Modal(document.getElementById('leaderboardModal'), {
            keyboard: false
        });
        leaderboardModal.show();
    } else {
        showNotification('Competition not found.', 'error');
    }
}



function getUsers() {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({ param: 1 }); // Fetch users

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    if (xhr.responseText) {
                        const users = JSON.parse(xhr.responseText);
                        allUsers = users;
                        showInfoUsers();
                        showNotification('Data retrieved.', 'success');

                    } else {
                        showNotification('Empty response received.', 'error');
                        console.error('Empty response received.');
                    }
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                    console.error('Response:', xhr.responseText);
                    showNotification('Error parsing users data.', 'error');
                }
            } else {
                console.error('Error fetching users:', xhr.status, xhr.statusText);
            }
        }
    };

    xhr.onerror = () => {
        console.error('Request failed');
    };
    xhr.send();
}

function showInfoUsers() {
    const tbody = document.getElementById('info-u').querySelector('tbody');
    tbody.innerHTML = '';

    allUsers.forEach(user => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.discipline.name}</td>
            <td>${user.competitions.length}</td>
            <td>
                <i class="fas fa-eye" title="See" onclick="viewUser(${user.id})"></i>
            </td>
        `;

        tbody.appendChild(row);
    });
}

function viewUser(userId) {
    const user = allUsers.find(u => u.id === userId);

    if (user) {
        // Populate the modal with user details
        document.getElementById('userId').innerText = user.id;
        document.getElementById('userName').innerText = user.name;
        document.getElementById('userLastName').innerText = user.lastName;
        document.getElementById('userAge').innerText = user.age;
        document.getElementById('userDiscipline').innerText = user.discipline.name;


        // Populate the competitions list
        const competitionsList = document.getElementById('userCompetitionsList');
        competitionsList.innerHTML = ''; // Clear previous list items
        user.competitions.forEach(competition => {
            const listItem = document.createElement('li');
            listItem.textContent = competition;
            competitionsList.appendChild(listItem);
        });

        // Show the modal
        const userDetailsModal = new bootstrap.Modal(document.getElementById('userDetailsModal'));
        userDetailsModal.show();
    } else {
        showNotification('User not found.', 'error');
    }
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
