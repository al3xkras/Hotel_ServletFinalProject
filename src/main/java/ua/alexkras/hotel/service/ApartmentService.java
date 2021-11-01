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

    private Optional<Apartment> currentApartment=Optional.empty();
    public void clearEverything(){
        clearCurrentApartment();
    }

    private Optional<Long> currentReservationId;
    private Optional<List<Apartment>> apartmentsMatchingCurrentReservation;


    public ApartmentService(){
        this.apartmentDAO = JDBCDaoFactory.getInstance().createApartmentDAO();
    }


    public List<Apartment> findApartments(int start, int total){
        return apartmentDAO.findApartments(start,total);
    }

    public Optional<Apartment> getApartmentById(Integer id){
        return apartmentDAO.findApartmentById(id);
    }

    public void addApartment(Apartment apartment) {
        /*apartmentDAO.save(apartment);
        clearCurrentApartment();
        clearApartmentsMatchingCurrentReservation();

         */
    }

    /**
     * Update Apartment's status by apartment id
     *
     * @param id Apartment's id
     * @param apartmentStatus New apartment status
     * @throws ???
     */
    public void updateApartmentStatusById(int id,ApartmentStatus apartmentStatus) {
        /*apartmentDAO.updateApartmentStatusById(id,apartmentStatus);
        clearCurrentApartment();
        clearApartmentsMatchingCurrentReservation();

         */
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

    public void clearCurrentApartment(){
        currentApartment=Optional.empty();
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

    public void clearApartmentsMatchingCurrentReservation(){
        apartmentsMatchingCurrentReservation=null;
    }

    public Apartment getCurrentApartment() {
        return currentApartment.orElseThrow(IllegalStateException::new);
    }

    public List<Apartment> getApartmentsMatchingCurrentReservation() {
        return apartmentsMatchingCurrentReservation.orElseThrow(IllegalStateException::new);
    }
}
