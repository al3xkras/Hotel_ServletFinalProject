package ua.alexkras.hotel.model;

public interface MySqlStrings {
    String root = "jdbc:mysql://localhost:3306/";
    String databaseName="hotel_db_servlet";
    String tableUser = "user";

    String colUserId = "id";
    String colUserName = "name";
    String colUserSurname = "surname";
    String colUserUsername = "username";
    String colUserPassword = "password";
    String colUserPhoneNumber = "phone_number";
    String colUserBirthday = "birthday";
    String colUserGender = "gender";
    String colUserUserType = "user_type";

    String[] tableUserColumns = new String[]{
        colUserId,colUserName, colUserSurname, colUserUsername,
                colUserPassword, colUserPhoneNumber, colUserBirthday,
                colUserGender, colUserUserType};

    String connectionUrl = root+databaseName+"?serverTimezone=UTC";

    String user = "root";
    String password = "root";


    String sqlCreateDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s;",
            databaseName);

    String sqlCreateUserTableIfNotExists = "CREATE TABLE IF NOT EXISTS " +
            MySqlStrings.databaseName+"."+MySqlStrings.tableUser+ " (" +
            colUserId+" int unique primary key auto_increment, " +
            colUserName+" varchar(25) not null," +
            colUserSurname+" varchar(30) not null," +
            colUserUsername+" varchar(15) unique not null," +
            colUserPassword+" varchar(20) not null," +
            colUserBirthday+" DATE not null," +
            colUserGender+" varchar(10)," +
            colUserPhoneNumber+" varchar(30) not null," +
            colUserUserType+" varchar(20) not null);";

    String getUserByUsername = "SELECT" +
            " id,name,surname,password,birthday,gender,phone_number,user_type " +
            "FROM "+MySqlStrings.databaseName+"."+ MySqlStrings.tableUser +" WHERE "+colUserUsername+"=?";

    String getUserById = "SELECT "+
            colUserName+','+
            colUserSurname+','+
            colUserUsername+','+
            colUserPassword+','+
            colUserBirthday+','+
            colUserGender+','+
            colUserPhoneNumber+','+
            colUserUserType+
            " FROM "+databaseName+','+tableUser+" WHERE "+colUserId+"=?";

    String addUser = "INSERT INTO "+MySqlStrings.databaseName+"."+MySqlStrings.tableUser+ " ("+
            colUserId+','+
            colUserName+ ','+
            colUserSurname+','+
            colUserUsername+','+
            colUserPassword+','+
            colUserBirthday+','+
            colUserGender+','+
            colUserPhoneNumber+','+
            colUserUserType+") VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

}
