package ua.alexkras.hotel.model.mysql;

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
            '@'+colApartmentPrice+" FROM "+MySqlStrings.databaseName+'.'+tableApartment+
            " WHERE "+colApartmentId+"=?; ";

    String addApartment = "INSERT INTO "+MySqlStrings.databaseName+"."+tableApartment+" ("+
            colApartmentId+','+
            colApartmentName+ ','+
            colApartmentPlaces+','+
            colApartmentClass+','+
            colApartmentStatus+','+
            colApartmentPrice+") VALUES "+
            "(null, ?, ?, ?, ?, ?)";

    String updateApartment = "UPDATE " +
            MySqlStrings.databaseName+'.'+tableApartment+
            " SET "+
            colApartmentName+"=?,"+
            colApartmentPlaces+"=?,"+
            colApartmentClass+"=?,"+
            colApartmentStatus+"=?,"+
            colApartmentPrice+"=?"+
            " WHERE "+colApartmentId+"=?";

    String deleteApartmentById = "DELETE FROM "+MySqlStrings.databaseName+"."+tableApartment+" WHERE "+colApartmentId+"=?";

    String selectApartmentsWithLimit = findAllApartments+" limit ?,?";

    String updateApartmentStatusById="UPDATE "+MySqlStrings.databaseName+'.'+tableApartment+" SET " +
            colApartmentStatus+"=? WHERE "+colApartmentId+"=?";

    String findByApartmentClassAndPlacesAndStatus = "SELECT * FROM hotel_db_servlet.apartments " +
            "WHERE apartment_class=? and places=? and status=? limit ?,?";

}
