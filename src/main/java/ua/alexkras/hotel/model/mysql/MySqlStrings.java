package ua.alexkras.hotel.model.mysql;

public interface MySqlStrings {
    String root = "jdbc:mysql://localhost:3306/";
    String databaseName="hotel_db_servlet";
    String connectionUrl = root+databaseName+"?serverTimezone=UTC";

    String user = "root";
    String password = "root";

    String sqlCreateDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s;",
            databaseName);
}
