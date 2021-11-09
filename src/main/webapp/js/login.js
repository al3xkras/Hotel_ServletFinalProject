const inputUsername = document.getElementById("username");
const inputPassword = document.getElementById("password");

if (sessionStorage.inputUsername){
    inputUsername.value=sessionStorage.inputUsername;
}

function savePageVariables() {
    sessionStorage.inputUsername = inputUsername.value;
}