package ua.alexkras.hotel.model.mysql;

public interface ReservationTableStrings {
    String tableReservation = "reservations";

    String colReservationId = "id";
    String colUserId = "user_id";
    String colApartmentId = "apartment_id";
    String colApartmentClass = "apartment_class";
    String colApartmentPlaces = "places";
    String colApartmentPrice = "price";
    String colReservationStatus="status";
    String colFromDate = "from_date";
    String colToDate = "to_date";
    String colSubmitDate = "submit_date";
    String colAdminConfirmationDate = "confirmation_date";
    String colIsPaid = "id_paid";
    String colIsActive = "is_active";
    String colIsExpired = "is_expired";

    String sqlCreateReservationTableIfNotExists = "CREATE TABLE IF NOT EXISTS "+
            MySqlStrings.databaseName+"."+tableReservation+" ("+
            colReservationId+" int unique primary key auto_increment, "+
            colUserId+" int not null,"+
            colApartmentId+" int not null,"+
            colApartmentClass+" varchar(20) not null,"+
            colApartmentPlaces+" int not null,"+
            colApartmentPrice+" int not null,"+
            colReservationStatus+" varchar(20) not null,"+
            colFromDate+" DATE not null,"+
            colToDate+" DATE not null,"+
            colSubmitDate+" DATE not null,"+
            colAdminConfirmationDate+" DATE not null,"+
            colIsPaid+" boolean default 0,"+
            colIsActive+" boolean default 1,"+
            colIsExpired+" boolean default 0);";

    String addReservation = "";
}
