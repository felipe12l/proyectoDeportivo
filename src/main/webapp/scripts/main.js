const general = document.getElementById("general")
const userSelect = document.getElementById("userSelect");
const infoC = document.getElementById("info-c");
const infoU = document.getElementById("info-u");



let allUsers = []

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
                    <th style="max-width: 50px">Action</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        `;
        showCompetitions();
    } else if (filterInfo.value === 'u') {
        tableContainer.innerHTML = `
            <table class="table table-bordered" id="info-u">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Last Name</th>
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

function showCompetitions() {

}

function getUsers() {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({ param: 1 }); // Fetch users

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    showNotification('Data retrieved.', 'success');
                    const users = JSON.parse(xhr.responseText);
                    allUsers = users;
                    showInfoUsers();
                } catch (e) {
                    console.error('Error parsing JSON:', e);
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
            <td>${user.CompetitionsCount}</td>
            <td>
                <i class="fas fa-eye" title="See" onclick="viewUser(${user.id})"></i>
            </td>
        `;

        tbody.appendChild(row);
    });
}

function viewUser(userId) {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({ param: userId }); // Fetch user details

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const user = JSON.parse(xhr.responseText);
                    // Populate the modal with user details
                    document.getElementById('userId').innerText = user.id;
                    document.getElementById('userName').innerText = user.name;
                    document.getElementById('userLastName').innerText = user.lastName;
                    document.getElementById('userEmail').innerText = user.email; // Adjust field name if necessary
                    document.getElementById('userCompetitionsCount').innerText = user.CompetitionsCount;

                    // Show the modal
                    const userDetailsModal = new bootstrap.Modal(document.getElementById('userDetailsModal'));
                    userDetailsModal.show();
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                    showNotification('Error parsing user details.', 'error');
                }
            } else {
                console.error('Error fetching user details:', xhr.status, xhr.statusText);
            }
        }
    };

    xhr.onerror = () => {
        console.error('Request failed');
    };
    xhr.send();
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
