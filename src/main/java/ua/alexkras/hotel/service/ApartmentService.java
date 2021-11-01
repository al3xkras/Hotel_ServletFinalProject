package ua.alexkras.hotel.service;


import ua.alexkras.hotel.dao.impl.JDBCApartmentDao;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;

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

    /**
     * Update current apartment by id
     * - If current apartment is present and has the same id, this method will do nothing
     * - If current apartment is not present, or has different id, new apartment will be requested from a database
     *
     * @param apartmentId - id of an Apartment
     * @return newly updated Apartment (or an existing one)
     * @throws IllegalStateException if Apartment was not found
     */
    public Apartment updateCurrentApartment(int apartmentId) {
        /*
        if (!currentApartment.isPresent() || currentApartment.get().getId()!=apartmentId){
            currentApartment = getApartmentById(apartmentId);
        }
        return getCurrentApartment();

         */
        return null;
    }

    /**
     * Update List of Apartments, that match current Reservation, referenced from ReservationService
     * -If List of apartments is not initialized,
     *   or is empty,
     *   or apartments in list do not match current Reservation:
     *   Requests List of apartments from a data source
     * -Otherwise:
     *   returns previously saved in memory list of apartments, that match reservation
     *
     * @return List of apartments, that match current Reservation, referenced from ReservationService
     */
    public List<Apartment> updateApartmentsMatchingCurrentReservation(){
        /*if (    !apartmentsMatchingCurrentReservation.isPresent() ||
                apartmentsMatchingCurrentReservation.get().isEmpty() ||
                !currentReservationId.isPresent() ||
                currentReservationId.get().longValue() == reservationService.getCurrentReservation().getId()) {

            apartmentsMatchingCurrentReservation =
                    Optional.of(findApartmentsMatchingReservation(reservationService.getCurrentReservation()));
            currentReservationId=Optional.of(reservationService.getCurrentReservation().getId());
        }
        return getApartmentsMatchingCurrentReservation();

         */
        return null;
    }
}
