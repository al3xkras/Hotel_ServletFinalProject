let today = new Date();
let dd = today.getDate();
let mm = today.getMonth() + 1; //January is 0!
const yyyy = today.getFullYear();
let hh = today.getHours();
let minutes = today.getMinutes();

if (dd < 10) {
    dd = '0' + dd;
}

if (mm < 10) {
    mm = '0' + mm;
}

if (hh < 10){
    hh = '0'+ hh;
}

if (minutes < 10){
    minutes = '0'+minutes;
}

today = yyyy + '-' + mm + '-' + dd + 'T' + hh + ':' + minutes;
const max_date = (yyyy + 5) + '-' + mm + '-' + dd + 'T00:00' + hh + ':' + minutes;

document.getElementById("from_date").min = today;
document.getElementById("from_date").max = max_date;
document.getElementById("to_date").min = today;
document.getElementById("to_date").max = max_date;