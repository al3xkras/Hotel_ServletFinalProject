package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.PaymentDAO;
import ua.alexkras.hotel.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.alexkras.hotel.model.mysql.PaymentTableStrings.*;

public class JDBCPaymentDao implements PaymentDAO {
    private final Connection connection;

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Payment payment) {
        try(PreparedStatement create = connection.prepareStatement("INSERT INTO hotel_db_servlet.payments (" +
                "id, " +
                "user_id, " +
                "reservation_id, " +
                "value, " +
                "payment_date, " +
                "card_number, " +
                "card_expiration_date, " +
                "card_cvv"+
                ") VALUES (null,?,?,?,?,?,?,?);")
                ){

            __(payment, create);

            create.execute();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public Optional<Payment> findById(long id) {
        Payment payment;
        try(PreparedStatement findById = connection.prepareStatement("SELECT " +
                "user_id," +
                "reservation_id," +
                "value," +
                "payment_date," +
                "card_number," +
                "card_expiration_date," +
                "card_cvv FROM hotel_db_servlet.payments " +
                " WHERE id=?")
                ){

            findById.setLong(1,id);

            ResultSet rs = findById.executeQuery();

            if (!rs.next()){
                return Optional.empty();
            }

            payment = getPaymentFromResultSet(rs);

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return Optional.of(payment);
    }

    @Override
    public List<Payment> findAll(int start, int total) {
        List<Payment> payments = new ArrayList<>();
        try(PreparedStatement findAll = connection.prepareStatement("SELECT " +
                "id," +
                "user_id," +
                "reservation_id," +
                "value," +
                "payment_date," +
                "card_number," +
                "card_expiration_date," +
                "card_cvv " +
                " FROM hotel_db_servlet.payments limit ?,?")){
            findAll.setLong(1,start-1);
            findAll.setLong(2,total);

            ResultSet rs = findAll.executeQuery();

            while (rs.next()){
                payments.add(getPaymentFromResultSet(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return payments;
    }

    @Override
    public void update(Payment payment) {
        try (PreparedStatement update = connection.prepareStatement("UPDATE hotel_db_servlet.payments SET " +
                "user_id=?," +
                "reservation_id=?," +
                "value=?," +
                "payment_date=?," +
                "card_number=?," +
                "card_expiration_date=?," +
                "card_cvv=? " +
                " WHERE id=?")
                ){

            __(payment, update);
            update.setLong(8,payment.getId());

            update.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void __(Payment payment, PreparedStatement statement) throws SQLException {
        statement.setLong(1,payment.getUserId());
        statement.setLong(2,payment.getReservationId());
        statement.setInt(3,payment.getValue());
        statement.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDate()));
        statement.setString(5,payment.getCardNumber());
        statement.setDate(6, Date.valueOf(payment.getCardExpirationDate()));
        statement.setString(7,payment.getCardCvv());
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement delete = connection.prepareStatement("DELETE FROM hotel_db_servlet.payments " +
                " WHERE id=?")
                ){

            delete.setLong(1,id);
            delete.execute();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected Payment getPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        return Payment.builder()
                .id(resultSet.getLong(colPaymentId))
                .userId(resultSet.getLong(colUserId))
                .reservationId(resultSet.getLong(colReservationId))
                .value(resultSet.getInt(colValue))
                .paymentDate(resultSet.getTimestamp(colPaymentDate).toLocalDateTime())
                .cardNumber(resultSet.getString(colCardNumber))
                .cardExpirationDate(resultSet.getDate(colCardExpirationDate).toLocalDate())
                .cardCvv(resultSet.getString(colCardCvv))
                .build();
    }
}
