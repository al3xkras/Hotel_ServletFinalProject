package ua.alexkras.hotel.service.impl;


import ua.alexkras.hotel.dao.impl.JDBCApartmentDao;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ApartmentService;

import java.util.List;
import java.util.Optional;


public class ApartmentServiceImpl implements ApartmentService<Pageable,Apartment> {
    private final JDBCApartmentDao apartmentDAO;

    public ApartmentServiceImpl(){
        this.apartmentDAO = JDBCDaoFactory.getInstance().createApartmentDAO();
    }

    @Override
    public void create(Apartment apartment) {
        apartmentDAO.create(apartment);
    }

    @Override
    public Optional<Apartment> findById(long id){
        return apartmentDAO.findById(id);
    }

    @Override
    public List<Apartment> findAllApartments(Pageable pageable){
        return apartmentDAO.findApartments(pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    /**
     * Get List of Apartments that can be assigned to a specific Reservation
     *
     * @param reservation reservation to match Apartments in list
     * @return List of apartments that match reservation
     */
    @Override
    public List<Apartment> findApartmentsMatchingReservation(Reservation reservation, Pageable pageable){
        return apartmentDAO.findApartmentsByApartmentClassAndPlacesAndStatus(
                reservation.getApartmentClass(), reservation.getPlaces(),
                ApartmentStatus.AVAILABLE, pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    public int getApartmentsCount(){
        return  apartmentDAO.getApartmentsCount();
    }

    public int getApartmentsMatchingReservationCount(Reservation reservation){
        return apartmentDAO.getApartmentsByApartmentClassAndPlacesAndStatusCount(
                reservation.getApartmentClass(), reservation.getPlaces(),
                ApartmentStatus.AVAILABLE);
    }

    /**
     * Update an Apartment entity
     * @param apartment Apartment to be updated
     */
    @Override
    public void updateApartment(Apartment apartment) {
        apartmentDAO.update(apartment);
    }

    @Override
    public void transactionalUpdateApartment(Apartment apartment){
        apartmentDAO.transactionalUpdateApartment(apartment);
    }

    @Override
    public void updateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
        apartmentDAO.commit();
    }

    @Override
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
