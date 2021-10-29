package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.PaymentDAO;
import ua.alexkras.hotel.entity.Payment;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCPaymentDao implements PaymentDAO {
    private final Connection connection;

    public JDBCPaymentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Payment entity) {

    }

    @Override
    public Optional<Payment> findById(long id) {
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public void update(Payment entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}
