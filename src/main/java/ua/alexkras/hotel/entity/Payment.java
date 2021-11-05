package ua.alexkras.hotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    private long id;
    private long userId;
    private long reservationId;
    private int value;
    private LocalDateTime paymentDate;
    private String cardNumber;
    private LocalDate cardExpirationDate;
    private String cardCvv;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", reservationId=" + reservationId +
                ", value=" + value +
                ", paymentDate=" + paymentDate +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardExpirationDate=" + cardExpirationDate +
                ", cardCvv='" + cardCvv + '\'' +
                '}';
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }


    public static final class PaymentBuilder {
        private long id;
        private long userId;
        private long reservationId;
        private int value;
        private LocalDateTime paymentDate;
        private String cardNumber;
        private LocalDate cardExpirationDate;
        private String cardCvv;

        private PaymentBuilder() {
        }

        public PaymentBuilder id(long id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public PaymentBuilder reservationId(long reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public PaymentBuilder value(int value) {
            this.value = value;
            return this;
        }

        public PaymentBuilder paymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public PaymentBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public PaymentBuilder cardExpirationDate(LocalDate cardExpirationDate) {
            this.cardExpirationDate = cardExpirationDate;
            return this;
        }

        public PaymentBuilder cardCvv(String cardCvv) {
            this.cardCvv = cardCvv;
            return this;
        }

        public Payment build() {
            Payment payment = new Payment();
            payment.id = this.id;
            payment.userId = this.userId;
            payment.cardExpirationDate = this.cardExpirationDate;
            payment.cardCvv = this.cardCvv;
            payment.reservationId = this.reservationId;
            payment.paymentDate = this.paymentDate;
            payment.value = this.value;
            payment.cardNumber = this.cardNumber;
            return payment;
        }
    }
}
