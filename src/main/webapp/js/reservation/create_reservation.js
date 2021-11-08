const fromDate= document.getElementById("from-date");
const toDate = document.getElementById("to-date");

apartmentClass = document.getElementById("apartment-class");
places = document.getElementById("places");

let today = new Date();
let dd = today.getDate();
let mm = today.getMonth() + 1; //January is 0!
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


if (sessionStorage.apartmentClass){
    apartmentClass.value=sessionStorage.apartmentClass;
}
if (sessionStorage.places){
    places.value=sessionStorage.places;
}
if (sessionStorage.fromDate){
    fromDate.value=sessionStorage.fromDate;
}
if (sessionStorage.toDate){
    toDate.value=sessionStorage.toDate;
}

function savePageVariables(){
    sessionStorage.apartmentClass=apartmentClass.value;
    sessionStorage.places=places.value;
    sessionStorage.fromDate=fromDate.value;
    sessionStorage.toDate=toDate.value;
}