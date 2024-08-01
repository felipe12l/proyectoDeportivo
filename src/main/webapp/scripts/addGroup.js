let allGroups = [];
let participantsToAdd = [];

function openAddGroupModal() {
    const modal = new bootstrap.Modal(document.getElementById('addGroupModal'));
    modal.show();
    getParticipants();
}

function addCompetition() {
    // Lógica para añadir la competencia
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

function showParticipantsToAdd(participants) {
    const table = document.getElementById('availableParticipantsTable').querySelector('tbody');
    table.innerHTML = '';
    participants.forEach(participant => {
        const row = document.createElement('tr');
        row.dataset.id = participant.id;
        row.innerHTML = `
            <td>${participant.id}</td>
            <td>${participant.name}</td>
            <td><i class="fas fa-plus" title="Add" onclick="selectParticipant(${participant.id})"></i></td>
        `;
        table.appendChild(row);
    });
}

function selectParticipant(participantId) {
    const participant = participantsToAdd.find(p => p.id === participantId);
    if (participant) {
        const selectedTable = document.getElementById('selectedParticipantsTable').querySelector('tbody');
        const row = document.createElement('tr');
        row.dataset.id = participant.id;
        row.innerHTML = `
            <td>${participant.id}</td>
            <td>${participant.name}</td>
            <td><i class="fas fa-minus" title="Remove" onclick="deselectParticipant(${participant.id})"></i></td>
        `;
        selectedTable.appendChild(row);

        // Remove from availableParticipants
        participantsToAdd = participantsToAdd.filter(p => p.id !== participantId);
        showParticipantsToAdd(participantsToAdd);
    }
}

function deselectParticipant(participantId) {
    const row = document.querySelector(`#selectedParticipantsTable tbody tr[data-id='${participantId}']`);
    if (row) {
        row.parentNode.removeChild(row);

        // Add back to availableParticipants
        const participant = { id: participantId, name: row.children[1].textContent };
        participantsToAdd.push(participant);
        showParticipantsToAdd(participantsToAdd);
    }
}

function addGroup() {
    const groupName = document.getElementById('groupName').value;
    if (groupName) {
        const selectedTable = document.getElementById('selectedParticipantsTable').querySelector('tbody');
        const participants = [];
        for (const row of selectedTable.children) {
            participants.push({ id: row.children[0].textContent, name: row.children[1].textContent });
        }
        allGroups.push({ name: groupName, participants: participants });
        showGroups();

        // Close the modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('addGroupModal'));
        modal.hide();
    } else {
        showNotification('Group name is required', 'error');
    }
}

function showGroups() {
    const table = document.getElementById('tableToAdd').querySelector('tbody');
    table.innerHTML = '';
    allGroups.forEach(group => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${group.name}</td>
            <td>${group.participants.length}</td>
            <td>
            <i class="fas fa-edit" title="Edit" onclick="editGroup('${group.name}')"></i>
            <i class="fas fa-trash" title="Delete" onclick="removeGroup('${group.name}')"></i>
            </td>
        `;
        table.appendChild(row);
    });
}

function editGroup(groupName) {
    const group = allGroups.find(g => g.name === groupName);
    if (group) {
        document.getElementById('editGroupName').value = group.name;
        document.getElementById('editGroupName').dataset.oldName = groupName;

        const availableTable = document.getElementById('editAvailableParticipantsTable').querySelector('tbody');
        availableTable.innerHTML = '';
        const selectedTable = document.getElementById('editSelectedParticipantsTable').querySelector('tbody');
        selectedTable.innerHTML = '';

        participantsToAdd.forEach(participant => {
            const row = document.createElement('tr');
            row.dataset.id = participant.id;
            row.innerHTML = `
                <td>${participant.id}</td>
                <td>${participant.name}</td>
                <td><i class="fas fa-plus" title="Add" onclick="selectParticipantForEdit(${participant.id})"></i></td>
            `;
            availableTable.appendChild(row);
        });

        group.participants.forEach(participant => {
            const row = document.createElement('tr');
            row.dataset.id = participant.id;
            row.innerHTML = `
                <td>${participant.id}</td>
                <td>${participant.name}</td>
                <td><i class="fas fa-minus" title="Remove" onclick="deselectParticipantForEdit(${participant.id})"></i></td>
            `;
            selectedTable.appendChild(row);
        });

        // Show edit modal
        const editModal = new bootstrap.Modal(document.getElementById('editGroupModal'));
        editModal.show();
    } else {
        showNotification('Group not found', 'error');
    }
}

function selectParticipantForEdit(participantId) {
    const participant = participantsToAdd.find(p => p.id === participantId);
    if (participant) {
        const selectedTable = document.getElementById('editSelectedParticipantsTable').querySelector('tbody');
        const row = document.createElement('tr');
        row.dataset.id = participant.id;
        row.innerHTML = `
            <td>${participant.id}</td>
            <td>${participant.name}</td>
            <td><i class="fas fa-minus" title="Remove" onclick="deselectParticipantForEdit(${participant.id})"></i></td>
        `;
        selectedTable.appendChild(row);

        // Remove from availableParticipants
        participantsToAdd = participantsToAdd.filter(p => p.id !== participantId);
        showParticipantsToAddForEdit(participantsToAdd);
    }
}

function deselectParticipantForEdit(participantId) {
    const row = document.querySelector(`#editSelectedParticipantsTable tbody tr[data-id='${participantId}']`);
    if (row) {
        row.parentNode.removeChild(row);

        // Add back to availableParticipants
        const participant = { id: participantId, name: row.children[1].textContent };
        participantsToAdd.push(participant);
        showParticipantsToAddForEdit(participantsToAdd);
    }
}

function showParticipantsToAddForEdit(participants) {
    const table = document.getElementById('editAvailableParticipantsTable').querySelector('tbody');
    table.innerHTML = '';
    participants.forEach(participant => {
        const row = document.createElement('tr');
        row.dataset.id = participant.id;
        row.innerHTML = `
            <td>${participant.id}</td>
            <td>${participant.name}</td>
            <td><i class="fas fa-plus" title="Add" onclick="selectParticipantForEdit(${participant.id})"></i></td>
        `;
        table.appendChild(row);
    });
}

function saveGroupEdit() {
    const newGroupName = document.getElementById('editGroupName').value;
    const oldGroupName = document.getElementById('editGroupName').dataset.oldName; // Save old name for comparison

    if (newGroupName) {
        // Find the group to update
        const groupIndex = allGroups.findIndex(g => g.name === oldGroupName);
        if (groupIndex > -1) {
            const selectedTable = document.getElementById('editSelectedParticipantsTable').querySelector('tbody');
            const participants = [];
            for (const row of selectedTable.children) {
                participants.push({ id: row.children[0].textContent, name: row.children[1].textContent });
            }

            // Update the group
            allGroups[groupIndex] = { name: newGroupName, participants: participants };
            showGroups();

            // Close the modal
            const editModal = bootstrap.Modal.getInstance(document.getElementById('editGroupModal'));
            editModal.hide();
        } else {
            showNotification('Group not found', 'error');
        }
    } else {
        showNotification('Group name is required', 'error');
    }
}

function removeGroup(groupName) {
    allGroups = allGroups.filter(group => group.name !== groupName);
    showGroups();
}

function back() {
    window.location.href = "main.html";
}
