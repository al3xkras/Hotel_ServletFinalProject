package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.dao.ConnectionPoolHolder;
import ua.alexkras.hotel.dao.DaoFactory;
import ua.alexkras.hotel.dao.impl.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    public Connection transactionalConnection,
            ApartmentDaoConnection,
            PaymentDaoConnection,
            ReservationDaoConnection,
            UserDaoConnection;

    public JDBCDaoFactory(){
        transactionalConnection = getConnection();
        try {
            transactionalConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JDBCApartmentDao createApartmentDAO() {
        ApartmentDaoConnection = getConnection();
        return new JDBCApartmentDao(ApartmentDaoConnection,transactionalConnection);
    }

    @Override
    public JDBCPaymentDao createPaymentDAO() {
        PaymentDaoConnection = getConnection();
        return new JDBCPaymentDao(PaymentDaoConnection,transactionalConnection);
    }

    @Override
    public JDBCReservationDao createReservationDAO() {
        ReservationDaoConnection = getConnection();
        return new JDBCReservationDao(ReservationDaoConnection,transactionalConnection);
    }

    @Override
    public JDBCUserDao createUserDAO() {
        UserDaoConnection = getConnection();
        return new JDBCUserDao(UserDaoConnection,transactionalConnection);
    }
}
