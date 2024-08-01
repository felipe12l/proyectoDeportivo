const general = document.getElementById("general")
const userSelect = document.getElementById("userSelect");

let allUsers = []

const filterSelect = document.getElementById("filterSelect");
filterSelect.value = 'c'






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

function getUsers() {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({ param: 1 }); // Fetch users

    xhr.open("GET", `/proyectoDeportivo_war_exploded/club-servlet?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        console.log(`ReadyState: ${xhr.readyState}, Status: ${xhr.status}`); // Mensajes de depuración

        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    showNotification('Data retrieved.', 'success');
                    const users = JSON.parse(xhr.responseText);
                    allUsers = users;
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
    const tbody = document.getElementById('info-u').querySelector('tbody')
    tbody.innerHTML = '';

    allUsers.forEach(user => {
        const row = document.createElement('tr');

        row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td></td>
                <td>
                    <button class="btn btn-info btn-sm" onclick="viewUser(${user.id})">View</button>
                </td>
            `;


        tbody.appendChild(row);
    });
}

function viewUser(id){

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
