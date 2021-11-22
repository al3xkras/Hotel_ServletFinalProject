package ua.alexkras.hotel.dao.impl;


import ua.alexkras.hotel.dao.DaoFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JDBCApartmentDao createApartmentDAO() {
        return new JDBCApartmentDao(getConnection());
    }

    @Override
    public JDBCPaymentDao createPaymentDAO() {
        return new JDBCPaymentDao(getConnection());
    }

    @Override
    public JDBCReservationDao createReservationDAO() {
        return new JDBCReservationDao(getConnection());
    }

    @Override
    public JDBCUserDao createUserDAO() {
        return new JDBCUserDao(getConnection());
    }
}
