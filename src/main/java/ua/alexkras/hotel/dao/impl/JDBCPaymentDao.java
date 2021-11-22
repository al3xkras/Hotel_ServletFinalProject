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
    public void create(Payment payment) {
        _create(payment, connection);
    }

    public void create(Payment payment, Connection connection){
        _create(payment, connection);
    }

    private void _create(Payment payment, Connection connection){
        try(PreparedStatement create = connection.prepareStatement(createPayment)
            ){
            setStatementValues(payment, create);

            create.execute();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Payment> findById(long id) {
        Payment payment;
        try(PreparedStatement findById = connection.prepareStatement(findPaymentById)
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
        try(PreparedStatement findAll = connection.prepareStatement(findAllPayments)){
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
        try (PreparedStatement update = connection.prepareStatement(updatePayment)
                ){

            setStatementValues(payment, update);
            update.setLong(8,payment.getId());

            update.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void setStatementValues(Payment payment, PreparedStatement statement) throws SQLException {
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
        try(PreparedStatement delete = connection.prepareStatement(deletePayment)
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
