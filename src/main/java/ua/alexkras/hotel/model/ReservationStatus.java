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

    private final boolean confirmable;
    private final boolean cancellable;
    private final String resourceName;
    private final String htmlClass;

    ReservationStatus(boolean confirmable, boolean cancellable,
                      String resourceName, String htmlClass){
        this.confirmable = confirmable;
        this.cancellable = cancellable;
        this.resourceName=resourceName;
        this.htmlClass=htmlClass;
    }

    public boolean isConfirmable() {
        return confirmable;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public String getHtmlClass() {
        return this.htmlClass;
    }

    public String getResourceName() {
        return this.resourceName;
    }
}
