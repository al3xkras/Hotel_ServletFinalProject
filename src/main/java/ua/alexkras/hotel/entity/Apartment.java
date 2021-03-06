package ua.alexkras.hotel.entity;


import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Apartment {
    private Long id;
    private String name;
    private int places;
    private ApartmentClass apartmentClass;
    private ApartmentStatus status;
    private Integer price;

    public boolean matchesReservation(@NotNull Reservation reservation){
        return places == reservation.getPlaces() &
                apartmentClass.equals(reservation.getApartmentClass()) &
                status.equals(ApartmentStatus.AVAILABLE);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPlaces() {
        return places;
    }

    public ApartmentClass getApartmentClass() {
        return apartmentClass;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", places=" + places +
                ", apartmentClass=" + apartmentClass +
                ", status=" + status +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return places == apartment.places && Objects.equals(id, apartment.id) && name.equals(apartment.name) && apartmentClass == apartment.apartmentClass && status == apartment.status && price.equals(apartment.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, places, apartmentClass, status, price);
    }

    public static ApartmentBuilder builder() {
        return new ApartmentBuilder();
    }

    public static final class ApartmentBuilder {
        private Long id;
        private String name;
        private int places;
        private ApartmentClass apartmentClass;
        private ApartmentStatus status;
        private Integer price;

        private ApartmentBuilder() {
        }

        public static ApartmentBuilder anApartment() {
            return new ApartmentBuilder();
        }

        public ApartmentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ApartmentBuilder places(int places) {
            this.places = places;
            return this;
        }

        public ApartmentBuilder apartmentClass(ApartmentClass apartmentClass) {
            this.apartmentClass = apartmentClass;
            return this;
        }

        public ApartmentBuilder status(ApartmentStatus status) {
            this.status = status;
            return this;
        }

        public ApartmentBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public Apartment build() {
            Apartment apartment = new Apartment();
            apartment.id = this.id;
            apartment.apartmentClass = this.apartmentClass;
            apartment.price = this.price;
            apartment.name = this.name;
            apartment.places = this.places;
            apartment.status = this.status;
            return apartment;
        }
    }
}
