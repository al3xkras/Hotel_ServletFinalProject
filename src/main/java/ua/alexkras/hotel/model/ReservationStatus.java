package ua.alexkras.hotel.model;

public enum ReservationStatus {
    PENDING(false, true, "reservation.status.pending",
            "reservation-status-pending"),
    CANCELLED(false, false,"reservation.status.cancelled",
            "reservation-status-cancelled"),
    CONFIRMED(true, true,"reservation.status.confirmed",
            "reservation-status-confirmed"),
    RESERVED(false, true,"reservation.status.reserved",
            "reservation-status-reserved"),
    EXPIRED(false, false,"reservation.status.expired",
            "reservation-status-expired");

    public boolean canConfirm;
    public boolean canDiscard;
    public String resourceName;
    public String htmlClass;

    ReservationStatus(boolean canConfirm, boolean canDiscard,
                      String resourceName, String htmlClass){
        this.canConfirm=canConfirm;
        this.canDiscard=canDiscard;
        this.resourceName=resourceName;
        this.htmlClass=htmlClass;
    }
}
