package ua.alexkras.hotel.entity;

import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private Long id;
    private Long userId;
    private ApartmentClass apartmentClass;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime submitDate;
    private LocalDate adminConfirmationDate;
    private int places;
    private Integer apartmentId;
    private Integer apartmentPrice;
    private boolean isPaid;
    private ReservationStatus reservationStatus;
    private boolean isActive = true;
    private boolean expired = false;

    //@Transient
    private Long daysUntilExpiration;

    public boolean isCompleted(){
        return apartmentId!=null & apartmentPrice!=null;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public ApartmentClass getApartmentClass() {
        return apartmentClass;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public LocalDate getAdminConfirmationDate() {
        return adminConfirmationDate;
    }

    public int getPlaces() {
        return places;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public Integer getApartmentPrice() {
        return apartmentPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isExpired() {
        return expired;
    }

    public Long getDaysUntilExpiration() {
        return daysUntilExpiration;
    }

    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    public static final class ReservationBuilder {
        private Long id;
        private Long userId;
        private Integer apartmentId;
        private ApartmentClass apartmentClass;
        private int places;
        private Integer apartmentPrice;
        private ReservationStatus reservationStatus;
        private LocalDate fromDate;
        private LocalDate toDate;
        private LocalDateTime submitDate;
        private LocalDate adminConfirmationDate;
        private boolean isPaid = false;
        private boolean isActive = true;
        private boolean expired = false;

        private Long daysUntilExpiration;

        private ReservationBuilder() {
        }



        public ReservationBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ReservationBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public ReservationBuilder apartmentClass(ApartmentClass apartmentClass) {
            this.apartmentClass = apartmentClass;
            return this;
        }

        public ReservationBuilder fromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public ReservationBuilder toDate(LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        public ReservationBuilder submitDate(LocalDateTime submitDate) {
            this.submitDate = submitDate;
            return this;
        }

        public ReservationBuilder adminConfirmationDate(LocalDate adminConfirmationDate) {
            this.adminConfirmationDate = adminConfirmationDate;
            return this;
        }

        public ReservationBuilder places(int places) {
            this.places = places;
            return this;
        }

        public ReservationBuilder apartmentId(Integer apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public ReservationBuilder apartmentPrice(Integer apartmentPrice) {
            this.apartmentPrice = apartmentPrice;
            return this;
        }

        public ReservationBuilder isPaid(boolean isPaid) {
            this.isPaid = isPaid;
            return this;
        }

        public ReservationBuilder reservationStatus(ReservationStatus reservationStatus) {
            this.reservationStatus = reservationStatus;
            return this;
        }

        public ReservationBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public ReservationBuilder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public ReservationBuilder daysUntilExpiration(Long daysUntilExpiration) {
            this.daysUntilExpiration = daysUntilExpiration;
            return this;
        }

        public Reservation build() {
            Reservation reservation = new Reservation();
            reservation.toDate = this.toDate;
            reservation.daysUntilExpiration = this.daysUntilExpiration;
            reservation.apartmentPrice = this.apartmentPrice;
            reservation.apartmentClass = this.apartmentClass;
            reservation.isPaid = this.isPaid;
            reservation.expired = this.expired;
            reservation.apartmentId = this.apartmentId;
            reservation.isActive = this.isActive;
            reservation.adminConfirmationDate = this.adminConfirmationDate;
            reservation.id = this.id;
            reservation.places = this.places;
            reservation.userId = this.userId;
            reservation.fromDate = this.fromDate;
            reservation.reservationStatus = this.reservationStatus;
            reservation.submitDate = this.submitDate;
            return reservation;
        }
    }
}
