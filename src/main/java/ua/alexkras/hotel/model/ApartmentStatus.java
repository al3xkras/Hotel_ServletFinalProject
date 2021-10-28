package ua.alexkras.hotel.model;

public enum ApartmentStatus {
    AVAILABLE("apartment.available","apartment-available"),
    RESERVED("apartment.reserved","apartment-reserved"),
    OCCUPIED("apartment.occupied","apartment-occupied"),
    UNAVAILABLE("apartment.unavailable","apartment-unavailable");

    String resName;
    String htmlClass;
    ApartmentStatus(String resName, String htmlClass){
        this.resName=resName;
        this.htmlClass=htmlClass;
    }

    public String getResName() {
        return resName;
    }
    public String getHtmlClass() {
        return htmlClass;
    }
}
