document.getElementById("expirationDate").min = new Date().toLocaleDateString('en-ca')

cardNumber = document.getElementById("cardNumber");
cardCvv = document.getElementById("cardCvv");
expirationDate=document.getElementById("expirationDate");

if (sessionStorage.cardNumber){
    cardNumber.value=sessionStorage.cardNumber;
}
if (sessionStorage.cardCvv){
    cardCvv.value=sessionStorage.cardCvv;
}
if (sessionStorage.expirationDate){
    expirationDate.value=sessionStorage.expirationDate;
}

function savePageVariables(){
    sessionStorage.cardNumber=cardNumber.value;
    sessionStorage.cardCvv=cardCvv.value;
    sessionStorage.expirationDate=expirationDate.value;
}