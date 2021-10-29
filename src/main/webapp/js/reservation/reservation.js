const dialog_select = document.getElementById("select-apartment");
const dialog_confirm_user = document.getElementById("confirm-reservation-user");
const dialog_discard_admin = document.getElementById("discard-reservation-admin");
const dialog_discard_user = document.getElementById("discard-reservation-user");
const dialog_confirm_admin = document.getElementById("confirm-reservation-admin");

if (apartmentSelected){
    dialog_select.showModal()
}

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

document.getElementById("cancel-select-apartment").addEventListener('click', function() {
    dialog_select.close();
});