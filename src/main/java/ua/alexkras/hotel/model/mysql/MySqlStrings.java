package ua.alexkras.hotel.model.mysql;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public interface MySqlStrings {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    ResourceBundle databaseBundle = ResourceBundle.getBundle("database");

    String root = databaseBundle.getString("connection.url.root");
    String databaseName= databaseBundle.getString("database.name");

    String connectionUrl = root+databaseName+"?serverTimezone=UTC";

    String user = databaseBundle.getString("username");
    String password = databaseBundle.getString("password");

    String sqlCreateDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s;",
            databaseName);
}
