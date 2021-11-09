const inputName = document.getElementById("name");
const inputSurname = document.getElementById("surname");
const inputUsername = document.getElementById("username");
const inputPassword = document.getElementById("password");
const inputPasswordConfirm = document.getElementById("password-confirm");
const inputBirthday = document.getElementById("birthday_date");
const inputGender = document.getElementsByName("gender");
const inputPhoneNumber = document.getElementById("phone-number");

document.getElementById("birthday_date").max = new Date().toLocaleDateString('en-ca')

if (sessionStorage.inputName){
    inputName.value=sessionStorage.inputName;
}if (sessionStorage.inputSurname){
    inputSurname.value=sessionStorage.inputSurname;
}if (sessionStorage.inputUsername){
    inputUsername.value=sessionStorage.inputUsername;
}if (sessionStorage.inputPassword){
    inputPassword.value=sessionStorage.inputPassword;
}if (sessionStorage.inputBirthday){
    inputBirthday.value=sessionStorage.inputBirthday;
}if (sessionStorage.inputGender){
    const gender = sessionStorage.inputGender;
    for(let i=0; i<inputGender.length; i++){
        if(inputGender[i].value === gender){
            inputGender[i].checked = true;
        }
    }
    inputGender.value=sessionStorage.inputGender;
}if (sessionStorage.inputPhoneNumber){
    inputPhoneNumber.value=sessionStorage.inputPhoneNumber;
}

function savePageVariables(){
    sessionStorage.inputName=inputName.value;
    sessionStorage.inputSurname=inputSurname.value;
    sessionStorage.inputUsername=inputUsername.value;
    sessionStorage.inputPassword=inputPassword.value;
    sessionStorage.inputBirthday=inputBirthday.value;
    for(let i=0; i<inputGender.length; i++){
        if(inputGender[i].checked){
            sessionStorage.inputGender=inputGender[i].value;
            break;
        }
    }
    sessionStorage.inputPhoneNumber=inputPhoneNumber.value;
}