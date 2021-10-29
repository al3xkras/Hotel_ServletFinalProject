package ua.alexkras.hotel.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private static Connection connection;

    protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(MySqlStrings.root + MySqlStrings.databaseName,
                MySqlStrings.user,
                MySqlStrings.password);
    }

    public static Connection get() throws SQLException, ClassNotFoundException {
        if (connection!=null && !connection.isClosed()){
            return connection;
        }
        connection = initializeDatabase();
        return connection;
    }

}

