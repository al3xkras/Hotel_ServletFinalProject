package ua.alexkras.hotel.service;


import ua.alexkras.hotel.dao.impl.JDBCApartmentDao;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.util.List;
import java.util.Optional;


public class ApartmentService {
    private final JDBCApartmentDao apartmentDAO;

    public ApartmentService(){
        this.apartmentDAO = JDBCDaoFactory.getInstance().createApartmentDAO();
    }

    public List<Apartment> findApartments(int start, int total){
        return apartmentDAO.findApartments(start,total);
    }

    public Optional<Apartment> getApartmentById(Integer id){
        return apartmentDAO.findById(id);
    }

    public void updateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
    }

    public boolean addApartment(Apartment apartment) {
        return apartmentDAO.create(apartment);
    }

    /**
     * Update Apartment
     * @param apartment Apartment to be updated
     */
    public void updateApartment(Apartment apartment) {
        apartmentDAO.update(apartment);
    }

    /**
     * Get List of Apartments that can be assigned to a specific Reservation
     *
     * @param reservation reservation to match Apartments in list
     * @return List of apartments
     */
    public List<Apartment> findApartmentsMatchingReservation(Reservation reservation){
        /*
        return apartmentDAO.findApartmentsByApartmentClassAndPlacesAndStatus(
                reservation.getApartmentClass(),
                reservation.getPlaces(),
                ApartmentStatus.AVAILABLE);

         */
        return null;
    }
}
