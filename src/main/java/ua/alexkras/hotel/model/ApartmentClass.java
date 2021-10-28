package ua.alexkras.hotel.model;

public enum ApartmentClass {
    ClassA("apartment.class_a","apartment-class-a"),
    ClassB("apartment.class_b","apartment-class-b"),
    ClassC("apartment.class_c","apartment-class-c"),
    ClassD("apartment.class_d","apartment-class-d");

    public String resName;
    public String htmlClass;

    ApartmentClass(String resName, String htmlClass){
        this.resName=resName;
        this.htmlClass=htmlClass;
    }
}
