

function showParticipantsToAdd(){

}

function showParticipantsAdded(){

}


function doGet(email, password){
    const xhr = new XMLHttpRequest()
    xhr.open("GET","shop-server",true)
    xhr.onreadystatechange = ()=>{
        if( xhr.readyState === 4 && xhr.status === 200){
            const users = JSON.parse(xhr.responseText);
            let userFound = false;

            users.forEach(u => {

                if (u.email === email) {
                    userFound = true;
                    if (u.password === password) {
                        login.style.display = "none";
                        logged.style.display = "block";
                        displayUsers(users)
                    } else {
                        showError(passwordError);
                    }
                }
            });
            if (!userFound) {
                showError(emailError);
            }

        }
    }

    xhr.send()
}



function addCompetition(){

}

function back(){
    window.location.href = "main.html"
}