let apartmentId=-1;

let _apartment_text = "";
let __apartment_text =  "";

const apartment_select_dialog_text = document.getElementById("select-apartment-text");
const apartment_select_dialog_form = document.getElementById("select-apartment-form");

let select_apartment_form_action = "";

const dialog_select_apartment = document.getElementById("select-apartment");
const dialog_confirm_user = document.getElementById("confirm-reservation-user");
const dialog_discard_admin = document.getElementById("discard-reservation-admin");
const dialog_discard_user = document.getElementById("discard-reservation-user");
const dialog_confirm_admin = document.getElementById("confirm-reservation-admin");

function discardReservationAdmin(){
    dialog_discard_admin.showModal()
}

function hideDiscardReservationAdmin(){
    dialog_discard_admin.close();
}

function confirmReservationUser(){
    dialog_confirm_user.showModal()
}

function hideConfirmReservationUser(){
    dialog_confirm_user.close()
}

function discardReservationUser(){
    dialog_discard_user.showModal()
}

function hideDiscardReservationUser(){
    dialog_discard_user.close();
}

function showConfirmReservationAdmin(){
    dialog_confirm_admin.showModal()
}

function hideConfirmReservationAdmin(){
    dialog_confirm_admin.close()
}

function selectApartment(id){
    apartmentId=id;
    apartment_select_dialog_text.innerHTML=_apartment_text+" "+id+" "+__apartment_text;
    apartment_select_dialog_form.setAttribute("action",select_apartment_form_action+apartmentId);

    dialog_select_apartment.showModal();
}

function insertParam(key, value) {
    key = encodeURIComponent(key);
    value = encodeURIComponent(value);

    const kvp = document.location.search.substr(1).split('&');
    let i=0;

    for(; i<kvp.length; i++){
        if (kvp[i].startsWith(key + '=')) {
            let pair = kvp[i].split('=');
            pair[1] = value;
            kvp[i] = pair.join('=');
            break;
        }
    }

    if(i >= kvp.length){
        kvp[kvp.length] = [key,value].join('=');
    }
    document.location.search = kvp.join('&');
}