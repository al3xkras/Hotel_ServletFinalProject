package ua.alexkras.hotel.service;

import ua.alexkras.hotel.entity.Payment;

import java.sql.Connection;

public interface PaymentService extends Service<Payment>{
    void create(Payment payment, Connection connection);
    Payment.PaymentBuilder paymentOf(String cardNumber, String CVV, String expirationDate);
}
