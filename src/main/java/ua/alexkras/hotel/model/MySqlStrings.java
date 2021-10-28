package ua.alexkras.hotel.model;

public interface MySqlStrings {
    String root = "jdbc:mysql://localhost:3306/";
    String databaseName="hotel_db";
    String tableUser = "user";

    String colUserId = "ID";
    String colUserName = "NAME";
    String colUserSurname = "SURNAME";
    String colUserUsername = "USERNAME";
    String colUserPassword = "PASSWORD";
    String colUserPhoneNumber = "PHONE_NUMBER";
    String colUserBirthday = "BIRTHDAY";
    String colUserGender = "GENDER";
    String colUserUserType = "USER_TYPE";

    String[] tableUserColumns = new String[]{
        colUserId,colUserName, colUserSurname, colUserUsername,
                colUserPassword, colUserPhoneNumber, colUserBirthday,
                colUserGender, colUserUserType};

    String connectionUrl = root+databaseName+"?serverTimezone=UTC";

    String user = "root";
    String password = "root";


    String sqlCreateDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s;",
            databaseName);

    String sqlSelectColumnsFromUserDB = "SELECT %s FROM " + databaseName + "." + tableUser;

}
