package ua.alexkras.hotel.model.mysql;

import ua.alexkras.hotel.model.ApartmentStatus;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.databaseName;

public interface ApartmentTableStrings {
    String tableApartment="apartments";

    String colApartmentId="id";
    String colApartmentName="name";
    String colApartmentPlaces="places";
    String colApartmentClass="apartment_class";
    String colApartmentStatus="status";
    String colApartmentPrice="price";

    String sqlCreateApartmentTableIfNotExists = "CREATE TABLE IF NOT EXISTS "+
            MySqlStrings.databaseName+"."+tableApartment+" ("+
            colApartmentId+" int unique primary key auto_increment, "+
            colApartmentName+" varchar(25) not null,"+
            colApartmentPlaces+" int not null,"+
            colApartmentClass+" varchar(20) not null,"+
            colApartmentStatus+" varchar(20) not null,"+
            colApartmentPrice+" int not null);";

    String findAllApartments = "SELECT "+
            colApartmentId+','+
            colApartmentName+','+
            colApartmentPlaces+','+
            colApartmentClass+','+
            colApartmentStatus+','+
            colApartmentPrice+
            " FROM "+MySqlStrings.databaseName+'.'+tableApartment;

    String findById=findAllApartments+" WHERE "+colApartmentId+"=?";

    String selectApartmentByIdIntoVariable = "SELECT "+
            colApartmentId+','+
            colApartmentPlaces+','+
            colApartmentClass+','+
            colApartmentPrice+" INTO " +
            '@'+colApartmentId+','+
            '@'+colApartmentPlaces+','+
            '@'+colApartmentClass+','+
            '@'+colApartmentPrice+" FROM "+databaseName+'.'+tableApartment+
            " WHERE "+colApartmentId+"=?; ";

    String addApartment = "INSERT INTO "+databaseName+"."+tableApartment+" ("+
            colApartmentId+','+
            colApartmentName+ ','+
            colApartmentPlaces+','+
            colApartmentClass+','+
            colApartmentStatus+','+
            colApartmentPrice+") VALUES "+
            "(null, ?, ?, ?, ?, ?)";

    String updateApartment = "UPDATE " +
            databaseName+'.'+tableApartment+
            " SET "+
            colApartmentName+"=?,"+
            colApartmentPlaces+"=?,"+
            colApartmentClass+"=?,"+
            colApartmentStatus+"=?,"+
            colApartmentPrice+"=?"+
            " WHERE "+colApartmentId+"=?";

    String deleteApartmentById = "DELETE FROM "+databaseName+"."+tableApartment+" WHERE "+colApartmentId+"=?";

    String selectApartmentsWithLimit = findAllApartments+" limit ?,?";

    String updateApartmentStatusById="UPDATE "+databaseName+'.'+tableApartment+" SET " +
            colApartmentStatus+"=? WHERE "+colApartmentId+"=?";

    String findByApartmentClassAndPlacesAndStatus = "SELECT * FROM "+databaseName+'.'+tableApartment+
            " WHERE "+colApartmentClass+"=? and "+
            colApartmentPlaces+"=? and "+
            colApartmentStatus+"=? limit ?,?";

    String truncateApartmentsTable = "TRUNCATE "+databaseName+'.'+tableApartment;

    String getApartmentsByApartmentClassAndPlacesAndStatusCount = "SELECT COUNT(*) FROM "+databaseName+'.'+tableApartment+
            " WHERE "+colApartmentClass+"=? and "+colApartmentPlaces+"=? and "+colApartmentStatus+"=?";

    String getApartmentsCount = "SELECT COUNT(*) from "+databaseName+'.'+tableApartment;

    String setExpiredReservationApartmentsAvailable = "UPDATE "+databaseName+'.'+tableApartment+" SET "+
            colApartmentStatus+"=? "+
            "WHERE "+colApartmentStatus+"='"+ ApartmentStatus.RESERVED+"' and "+
            colApartmentId+" IN " +
            "(SELECT "+ReservationTableStrings.colApartmentId+" FROM " +
            databaseName+"."+ReservationTableStrings.tableReservation+" WHERE " +
            ReservationTableStrings.colIsExpired+" and "+ReservationTableStrings.colIsActive+")";


}
