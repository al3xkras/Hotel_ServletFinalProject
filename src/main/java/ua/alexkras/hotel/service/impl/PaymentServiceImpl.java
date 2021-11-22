package ua.alexkras.hotel.service.impl;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCPaymentDao;
import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.mysql.MySqlStrings;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.Service;

import java.sql.Connection;
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

    /**
     * Add new payment entity to a data source
     * @param payment valid payment to be created
     * @throws RuntimeException if @payment is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    @Override
    public void create(Payment payment){
        paymentDAO.create(payment);
    }

    /**
     * Add new payment entity to a data source in transaction (without commit)
     * @param payment valid payment to be created
     * @throws RuntimeException if @payment is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    public void create(Payment payment, Connection connection){
        paymentDAO.create(payment, connection);
    }

    /**
     * Find payment entity by id
     * @param id id of a payment
     * @return Optional of payment if present in a data source, Optional.empty() otherwise
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public Optional<Payment> findById(long id){
        return paymentDAO.findById(id);
    }

    /**
     * Create payment of String values @cardNumber, @CVV, @expirationDate
     * @param cardNumber payment's card number
     * @param CVV payment's card CVV code
     * @param expirationDate payment's card expiration date
     * @return new Payment entity, that has corresponding fields set to input parameters
     * @throws ParseException if @expirationDate's format does not match @MySqlStrings.dateFormat
     * @throws NumberFormatException if CVV code is invalid (does not match regex: "^([0-9]{3})$")
     */
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
