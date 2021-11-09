package ua.alexkras.hotel.model.mysql;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.databaseName;

public interface ReservationTableStrings {
    String tableReservation = "reservations";

    String colReservationId = "id";
    String colUserId = "user_id";
    String colApartmentId = "apartment_id";
    String colApartmentClass = "apartment_class";
    String colApartmentPlaces = "places";
    String colApartmentPrice = "price";
    String colReservationStatus="reservation_status";
    String colFromDate = "from_date";
    String colToDate = "to_date";
    String colSubmitDate = "submit_date";
    String colAdminConfirmationDate = "confirmation_date";
    String colIsPaid = "id_paid";
    String colIsActive = "is_active";
    String colIsExpired = "is_expired";

    String sqlCreateReservationTableIfNotExists = "CREATE TABLE IF NOT EXISTS "+
            databaseName+"."+tableReservation+" ("+
            colReservationId+" int unique primary key auto_increment, "+
            colUserId+" int not null,"+
            colApartmentId+" int,"+
            colApartmentClass+" varchar(20) not null,"+
            colApartmentPlaces+" int not null,"+
            colApartmentPrice+" int,"+
            colReservationStatus+" varchar(20) not null,"+
            colFromDate+" DATE not null,"+
            colToDate+" DATE not null,"+
            colSubmitDate+" DATETIME not null,"+
            colAdminConfirmationDate+" DATE,"+
            colIsPaid+" boolean default 0,"+
            colIsActive+" boolean default 1,"+
            colIsExpired+" boolean default 0);";

    String addReservation = "INSERT INTO "+ databaseName+"."+tableReservation+
            '('+colReservationId+','+
            colUserId+','+
            colApartmentId+','+
            colApartmentClass+','+
            colApartmentPlaces+','+
            colApartmentPrice+','+
            colReservationStatus+','+
            colFromDate+','+
            colToDate+','+
            colSubmitDate+','+
            colAdminConfirmationDate+','+
            colIsPaid+','+
            colIsActive+','+
            colIsExpired+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    String selectReservations ="select "+
            colReservationId+','+
            colUserId+','+
            colApartmentId+','+
            colApartmentClass+','+
            colApartmentPlaces+','+
            colApartmentPrice+','+
            colReservationStatus+','+
            colFromDate+','+
            colToDate+','+
            colSubmitDate+','+
            colAdminConfirmationDate+','+
            colIsPaid+','+
            colIsActive+','+
            colIsExpired+
            " from "+ databaseName+"."+tableReservation;

    String selectActiveReservations = selectReservations+" WHERE "+colIsActive+"=?";

    String selectActiveReservationsByStatusWithLimit = selectActiveReservations+
            " AND "+colReservationStatus+"=?"+
            " limit ?,?";

    String selectActiveReservationsByUserIdAndAnyStatusExceptWithLimit = selectActiveReservations +
            " AND "+colUserId+"=?" +
            " AND "+colReservationStatus+"!=?"+
            " limit ?,?";

    String selectReservationById = selectReservations+" WHERE "+colReservationId+"=?";

    String updateStatusById = "UPDATE "+ databaseName+'.'+tableReservation+" SET "+colReservationStatus+"=? WHERE id=?";

    String updateReservationByIdAndUserIdWithApartment =
            "UPDATE "+ databaseName+'.'+tableReservation+" SET " +
                    colApartmentId+"=@"+ApartmentTableStrings.colApartmentId+',' +
                    colApartmentClass+"=@"+ApartmentTableStrings.colApartmentClass+',' +
                    colApartmentPlaces+"=@"+ApartmentTableStrings.colApartmentPlaces+',' +
                    colApartmentPrice+"=@"+ApartmentTableStrings.colApartmentPrice+',' +
                    colAdminConfirmationDate+"=?," +
                    colReservationStatus+"=?" +
                    " WHERE "+colReservationId+"=?;";

    String updateStatusAndConfirmationDateById = "UPDATE "+ databaseName+'.'+tableReservation+" SET "+
            colReservationStatus+"=?," +
            colAdminConfirmationDate+"=? WHERE id=?";

    String truncateReservationTable = "TRUNCATE "+ databaseName+'.'+tableReservation;

    String getReservationsCountByUserIdAndActiveAndAnyStatusExcept = "SELECT COUNT(*) FROM "+databaseName+'.'+tableReservation+
            " WHERE "+colUserId+"=? and "+colIsActive+"=? and "+colReservationStatus+"!=?";

    String getReservationsCountByActiveAndStatus = "SELECT COUNT(*) FROM "+databaseName+'.'+tableReservation+
            " WHERE "+colIsActive+"=? and "+colReservationStatus+"=?";

    String updateIsPaidById = "UPDATE "+databaseName+'.'+tableReservation+" SET " +
            colIsPaid+"=? WHERE "+colReservationId+"=?";

    String updateAllExpiredReservations = "UPDATE " +
            databaseName+'.'+tableReservation+" SET " +
            colReservationStatus+"=?,"+
            colIsExpired+"=true "+
            "WHERE not "+colIsExpired+" and not "+colIsPaid+" and " +
            colAdminConfirmationDate+" is not null and " +
            "DATEDIFF("+colAdminConfirmationDate+",?)>=?";

    String updateActiveReservations = "UPDATE " +
            databaseName+'.'+tableReservation+" SET " +
            colIsActive+"=false "+
            "WHERE "+colIsActive+" and "+colIsExpired;

}
