package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCPaymentDao;
import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.mysql.MySqlStrings;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class PaymentService {

    private final JDBCPaymentDao paymentDAO;

    public PaymentService(ReservationService reservationService){
        this.paymentDAO = JDBCDaoFactory.getInstance().createPaymentDAO();
    }

    public void addPayment(Payment payment){
        paymentDAO.create(payment);
    }

    public Optional<Payment> findById(int reservationId){
        return paymentDAO.findById(reservationId);
    }

    public Payment.PaymentBuilder paymentOf(String cardNumber, String CVV, String expirationDate) throws ParseException {
        if (!CVV.matches("^([0-9]{3})$"))
            throw new NumberFormatException();

        return Payment.builder()
                .cardNumber(cardNumber)
                .cardCvv(CVV)
                .cardExpirationDate(MySqlStrings.dateFormat.parse(expirationDate)
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
