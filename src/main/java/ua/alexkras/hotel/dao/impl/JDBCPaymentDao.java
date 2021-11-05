package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.PaymentDAO;
import ua.alexkras.hotel.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCPaymentDao implements PaymentDAO {
    private final Connection connection;

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Payment entity) {

        try(PreparedStatement create = connection.prepareStatement("INSERT INTO hotel_db_servlet.payments (" +
                "id, " +
                "user_id, " +
                "reservation_id, " +
                "value, " +
                "payment_date, " +
                "card_number, " +
                "card_expiration_date, " +
                "card_cvv"+
                ") VALUES (null,?,?,?,?,?,?,?);");
            ){
            //TODO set parameters before execute
            create.execute();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return false;
    }

    @Override
    public Optional<Payment> findById(long id) {

        try(PreparedStatement findById = connection.prepareStatement("SELECT " +
                "user_id," +
                "reservation_id," +
                "value," +
                "payment_date," +
                "card_number," +
                "card_expiration_date," +
                "card_cvv FROM hotel_db_servlet.payments " +
                " WHERE id=?")){
            //TODO set parameters before execute
            ResultSet rs = findById.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public void update(Payment entity) {
        try (PreparedStatement update = connection.prepareStatement("UPDATE hotel_db_servlet.payments SET " +
                "user_id=?," +
                "reservation_id=?," +
                "value=?," +
                "payment_date=?," +
                "card_number=?," +
                "card_expiration_date=?," +
                "card_cvv=? " +
                " WHERE id=?");
            ){

            //TODO set parameters before execute
            update.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}
