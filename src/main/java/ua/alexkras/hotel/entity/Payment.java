package ua.alexkras.hotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    /*@Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, length = 32)

     */
    private int id;

    //@Column(name = "USER_ID", nullable = false, length = 32)
    private int userId;

    //@Column(name = "RESERVATION_ID", nullable = false, length = 32)
    private int reservationId;

    //@Column(name = "VALUE", nullable = false)
    private int value;

    //@Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDateTime paymentDate;

    //@Column(name = "CARD_NUMBER", nullable = false)
    private String cardNumber;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //@Column(name = "CARD_EXPIRATION_DATE", nullable = false)
    private LocalDate cardExpirationDate;

    //@Column(name = "CARD_CVV", nullable = false, length = 3)
    private String cardCvv;

}
