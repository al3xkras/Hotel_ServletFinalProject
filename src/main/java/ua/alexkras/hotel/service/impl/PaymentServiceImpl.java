package ua.alexkras.hotel.service.impl;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCPaymentDao;
import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.mysql.MySqlStrings;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService<Pageable,Payment> {

    private final JDBCPaymentDao paymentDAO;

    public PaymentServiceImpl(){
        this.paymentDAO = JDBCDaoFactory.getInstance().createPaymentDAO();
    }

    @Override
    public void create(Payment payment){
        paymentDAO.create(payment);
    }

    public void transactionalAddPayment(Payment payment){
        paymentDAO.createInTransaction(payment);
    }

    @Override
    public Optional<Payment> findById(long reservationId){
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


    @Override
    public void commitCurrentTransaction(){
        paymentDAO.commit();
    }

    @Override
    public void rollbackConnection(){
        paymentDAO.rollback();
    }
}
