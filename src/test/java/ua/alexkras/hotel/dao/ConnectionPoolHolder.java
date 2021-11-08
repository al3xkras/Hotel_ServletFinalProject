package ua.alexkras.hotel.dao;

import org.apache.commons.dbcp.BasicDataSource;
import ua.alexkras.hotel.model.mysql.MySqlStrings;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ua.alexkras.hotel.dao.impl.ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(MySqlStrings.connectionUrl);
                    ds.setUsername(MySqlStrings.user);
                    ds.setPassword(MySqlStrings.password);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }
}
