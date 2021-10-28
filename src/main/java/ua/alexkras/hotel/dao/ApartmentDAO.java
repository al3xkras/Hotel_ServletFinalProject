package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import java.util.List;
import java.util.Optional;

public class ApartmentDAO {
    Optional<Apartment> findApartmentById(Integer id){
        return null;
    }

    //@Query("update Apartment apartment set apartment.status =:apartmentStatus where apartment.id =:id")
    void updateApartmentStatusById(int id, ApartmentStatus apartmentStatus){

    }


    List<Apartment> findApartmentsByApartmentClassAndPlacesAndStatus(
            ApartmentClass apartmentClass, int places, ApartmentStatus apartmentStatus){
        return null;
    }

    List<Apartment> findAll(){
        return null;
    }
}
