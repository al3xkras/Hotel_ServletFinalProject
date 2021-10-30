package ua.alexkras.hotel.model.mysql;

public interface UserTableStrings {
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


    String sqlCreateUserTableIfNotExists = "CREATE TABLE IF NOT EXISTS " +
            MySqlStrings.databaseName+"."+ UserTableStrings.tableUser+ " (" +
            colUserId+" int unique primary key auto_increment, " +
            colUserName+" varchar(25) not null," +
            colUserSurname+" varchar(30) not null," +
            colUserUsername+" varchar(15) unique not null," +
            colUserPassword+" varchar(20) not null," +
            colUserBirthday+" DATE not null," +
            colUserGender+" varchar(10)," +
            colUserPhoneNumber+" varchar(30) not null," +
            colUserUserType+" varchar(20) not null);";

    String findAllUsers = "SELECT "+
            colUserId+','+
            colUserName+','+
            colUserSurname+','+
            colUserUsername+','+
            colUserPassword+','+
            colUserBirthday+','+
            colUserGender+','+
            colUserPhoneNumber+','+
            colUserUserType+
            " FROM "+MySqlStrings.databaseName+'.'+tableUser;

    String findByUsername = findAllUsers +" WHERE "+colUserUsername+"=?";

    String findById = findAllUsers+" WHERE "+colUserId+"=?";

    String addUser = "INSERT INTO "+ MySqlStrings.databaseName+"."+ UserTableStrings.tableUser+ " ("+
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

    String updateUser = "UPDATE " +
            MySqlStrings.databaseName+'.'+tableUser+
            " SET "+
            colUserName+ "=?,"+
            colUserSurname+"=?,"+
            colUserUsername+"=?,"+
            colUserPassword+"=?,"+
            colUserBirthday+"=?,"+
            colUserGender+"=?,"+
            colUserPhoneNumber+"=?,"+
            colUserUserType+"=?"+
            " WHERE "+colUserId+"=?";

    String deleteUserById="DELETE FROM "+MySqlStrings.databaseName+"."+tableUser+" WHERE "+colUserId+"=?";
}
