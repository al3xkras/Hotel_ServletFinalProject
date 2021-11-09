package ua.alexkras.hotel.model.mysql;

import java.text.SimpleDateFormat;

public interface MySqlStrings {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String root = "jdbc:mysql://localhost:3306/";
    String databaseName="hotel_db_servlet_test";
    String connectionUrl = root+databaseName+"?serverTimezone=UTC";

    String user = "root";
    String password = "root";

    String sqlCreateDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s;",
            databaseName);
}
