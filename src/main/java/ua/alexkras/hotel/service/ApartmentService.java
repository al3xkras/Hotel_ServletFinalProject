package ua.alexkras.hotel.service;


import ua.alexkras.hotel.dao.impl.JDBCApartmentDao;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentStatus;

import java.util.List;
import java.util.Optional;


public class ApartmentService implements Service{
    private final JDBCApartmentDao apartmentDAO;

    public ApartmentService(){
        this.apartmentDAO = JDBCDaoFactory.getInstance().createApartmentDAO();
    }

    public void addApartment(Apartment apartment) {
        apartmentDAO.create(apartment);
    }

    public Optional<Apartment> findById(long id){
        return apartmentDAO.findById(id);
    }

    public List<Apartment> findAllApartments(int start, int total){
        return apartmentDAO.findApartments(start,total);
    }

    /**
     * Get List of Apartments that can be assigned to a specific Reservation
     *
     * @param reservation reservation to match Apartments in list
     * @return List of apartments that match reservation
     */
    public List<Apartment> findApartmentsMatchingReservation(Reservation reservation, int start, int total){
        return apartmentDAO.findApartmentsByApartmentClassAndPlacesAndStatus(
                reservation.getApartmentClass(), reservation.getPlaces(),
                ApartmentStatus.AVAILABLE, start, total);
    }

    public int getApartmentsCount(){
        return  apartmentDAO.getApartmentsCount();
    }

    /**
     * Update an Apartment entity
     * @param apartment Apartment to be updated
     */
    public void updateApartment(Apartment apartment) {
        apartmentDAO.update(apartment);
    }

    public void transactionalUpdateApartment(Apartment apartment){
        apartmentDAO.transactionalUpdateApartment(apartment);
    }

    public void updateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
        apartmentDAO.commit();
    }

    public void transactionalUpdateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
    }

    @Override
    public void commitCurrentTransaction(){
        apartmentDAO.commit();
    }

    @Override
    public void rollbackConnection(){
        apartmentDAO.rollback();
    }
}
