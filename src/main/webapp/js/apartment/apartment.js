const fromDate= document.getElementById("fromDate");
const toDate = document.getElementById("toDate");

const updateButton = document.getElementById('create_reservation');
const cancelButton = document.getElementById('cancel');
const confirmationDialog = document.getElementById('confirmation_dialog');

if (sessionStorage.fromDate){
    fromDate.value=sessionStorage.fromDateApartmentPage;
}
if (sessionStorage.toDate){
    toDate.value=sessionStorage.toDateApartmentPage;
}

function savePageVariables(){
    sessionStorage.fromDateApartmentPage=fromDate.value;
    sessionStorage.toDateApartmentPage=toDate.value;
}

updateButton.addEventListener('click', function() {
    confirmationDialog.showModal();
});

cancelButton.addEventListener('click', function() {
    confirmationDialog.close();
});

let today = new Date();
let dd = today.getDate();
let mm = today.getMonth() + 1; //January is 0
const yyyy = today.getFullYear();

if (dd < 10) {
    dd = '0' + dd;
}

if (mm < 10) {
    mm = '0' + mm;
}

today = yyyy + '-' + mm + '-' + dd;
const max_date = (yyyy + 5) + '-' + mm + '-' + dd;

fromDate.min = today;
fromDate.max = max_date;
toDate.min = today;
toDate.max = max_date;

