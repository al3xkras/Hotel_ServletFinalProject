package ua.alexkras.hotel.entity;

import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    /*@Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, length = 32)
     */
    private int id;

    //@Column(name = "USER_ID", nullable = false)
    private int userId;

    //@Column(name = "APARTMENT_CLASS", nullable = false)
    //@Enumerated(EnumType.STRING)
    private ApartmentClass apartmentClass;


    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@Column(name = "FROM_DATE", nullable = false)
    private LocalDateTime fromDate;

    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@Column(name = "TO_DATE", nullable = false)
    private LocalDateTime toDate;

    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@Column(name = "SUBMIT_DATE", nullable = false)
    private LocalDateTime submitDate;

    //@Column(name = "ADMIN_CONFIRMATION_DATE")
    private LocalDate adminConfirmationDate;

    //@Column(name = "PLACES",nullable = false)
    private int places;

    //@Column(name = "APARTMENT_ID")
    private Integer apartmentId;

    //@Column(name = "APARTMENT_PRICE")
    private Integer apartmentPrice;

    //@Column(name = "IS_PAID", nullable = false)
    private boolean isPaid = false;

    //@Column(name="STATUS", nullable = false)
    //@Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    //@Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "boolean default 1")
    private boolean isActive = true;

    //@Column(name = "EXPIRED", nullable = false, columnDefinition = "boolean default 0")
    private boolean expired = false;

    //@Transient
    private Long daysUntilExpiration;

    public boolean isCompleted(){
        return apartmentId!=null & apartmentPrice!=null;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public ApartmentClass getApartmentClass() {
        return apartmentClass;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
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


    public static final class ReservationBuilder {
        /*@Id
               @GeneratedValue
               @Column(name = "ID", nullable = false, length = 32)
                */
        private int id;
        //@Column(name = "USER_ID", nullable = false)
        private int userId;
        //@Column(name = "APARTMENT_CLASS", nullable = false)
        //@Enumerated(EnumType.STRING)
        private ApartmentClass apartmentClass;
        //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        //@Column(name = "FROM_DATE", nullable = false)
        private LocalDateTime fromDate;
        //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        //@Column(name = "TO_DATE", nullable = false)
        private LocalDateTime toDate;
        //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        //@Column(name = "SUBMIT_DATE", nullable = false)
        private LocalDateTime submitDate;
        //@Column(name = "ADMIN_CONFIRMATION_DATE")
        private LocalDate adminConfirmationDate;
        //@Column(name = "PLACES",nullable = false)
        private int places;
        //@Column(name = "APARTMENT_ID")
        private Integer apartmentId;
        //@Column(name = "APARTMENT_PRICE")
        private Integer apartmentPrice;
        //@Column(name = "IS_PAID", nullable = false)
        private boolean isPaid = false;
        //@Column(name="STATUS", nullable = false)
        //@Enumerated(EnumType.STRING)
        private ReservationStatus reservationStatus;
        //@Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "boolean default 1")
        private boolean isActive = true;
        //@Column(name = "EXPIRED", nullable = false, columnDefinition = "boolean default 0")
        private boolean expired = false;
        //@Transient
        private Long daysUntilExpiration;

        private ReservationBuilder() {
        }

        public static ReservationBuilder aReservation() {
            return new ReservationBuilder();
        }

        public ReservationBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ReservationBuilder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public ReservationBuilder apartmentClass(ApartmentClass apartmentClass) {
            this.apartmentClass = apartmentClass;
            return this;
        }

        public ReservationBuilder fromDate(LocalDateTime fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public ReservationBuilder toDate(LocalDateTime toDate) {
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
