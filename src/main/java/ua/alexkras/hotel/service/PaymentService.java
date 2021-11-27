package ua.alexkras.hotel.service;

import ua.alexkras.hotel.entity.Payment;

import java.sql.Connection;

public interface PaymentService<Pageable> extends Service<Pageable,Payment>{
    void create(Payment payment, Connection connection);
    Payment.PaymentBuilder paymentOf(String cardNumber, String CVV, String expirationDate);
}
