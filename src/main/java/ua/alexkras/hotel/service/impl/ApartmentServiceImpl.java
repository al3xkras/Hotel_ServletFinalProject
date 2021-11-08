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

    /**
     * Add new apartment entity to a data source
     * @param apartment valid apartment to be created
     * @throws RuntimeException if @apartment is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    @Override
    public void create(Apartment apartment) {
        apartmentDAO.create(apartment);
    }

    /**
     * Find apartment entity by id
     * @param id id of an apartment
     * @return Optional of apartment if present in a data source, Optional.empty() otherwise
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public Optional<Apartment> findById(long id){
        return apartmentDAO.findById(id);
    }

    /**
     * Find all apartments in a data source, limited by @pageable
     * @param pageable Page, that limits amount of apartments to get from the data source
     * -retrieves a total of pageable.getEntriesInPage() entries, starting from pageable.getEntriesStart() item
     * @return List of apartments
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public List<Apartment> findAllApartments(Pageable pageable){
        return apartmentDAO.findApartments(pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    /**
     * Get List of Apartments that can be assigned to a specific Reservation
     *
     * @param reservation reservation to match Apartments in list
     * @return List of apartments that match reservation
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public List<Apartment> findApartmentsMatchingReservation(Reservation reservation, Pageable pageable){
        return apartmentDAO.findApartmentsByApartmentClassAndPlacesAndStatus(
                reservation.getApartmentClass(), reservation.getPlaces(),
                ApartmentStatus.AVAILABLE, pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    /**
     * Count apartments in a data source
     * @return count of apartments
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    public int getApartmentsCount(){
        return  apartmentDAO.getApartmentsCount();
    }

    /**
     * Count apartments in a data source that match specific reservation
     *  An apartment matches reservation if and only if:
     *    -apartment class == reservation's apartment class
     *    -apartment places == reservation's apartment places
     *
     * @param reservation reservation, to find matching apartments for
     * @return count of apartments in a data source, that match @reservation
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    public int getApartmentsMatchingReservationCount(Reservation reservation){
        return apartmentDAO.getApartmentsByApartmentClassAndPlacesAndStatusCount(
                reservation.getApartmentClass(), reservation.getPlaces(),
                ApartmentStatus.AVAILABLE);
    }

    /**
     * Update an Apartment entity
     * @param apartment Apartment to be updated
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void updateApartment(Apartment apartment) {
        apartmentDAO.update(apartment);
    }

    /**
     * Update an Apartment entity in current transaction (without committing an update)
     * @param apartment Apartment to be updated
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void transactionalUpdateApartment(Apartment apartment){
        apartmentDAO.transactionalUpdateApartment(apartment);
    }

    /**
     * Update apartment entity's status by id
     *
     * @param id id of an apartment to be updated
     * @param status apartment's status after update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void updateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
        apartmentDAO.commit();
    }

    /**
     * Update apartment entity's status by id in current transaction (without commit)
     *
     * @param id id of an apartment to be updated
     * @param status apartment's status after update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void transactionalUpdateApartmentStatusById(long id, ApartmentStatus status){
        apartmentDAO.updateApartmentStatusById(id,status);
    }

    /**
     * Commit current transaction (transactional connection),
     * - Committing a transaction from this service also
     *  commits methods that use transactional connection
     *  from other services
     * @throws RuntimeException if failed to commit transaction
     */
    @Override
    public void commitCurrentTransaction(){
        apartmentDAO.commit();
    }

    /**
     * Rollback transactional connection
     * - Rollback made from this service also
     *  affects methods that use transactional connection
     *  from other services (they will be discarded as well)
     * @throws RuntimeException if failed to rollback transactional connection
     */
    @Override
    public void rollbackConnection(){
        apartmentDAO.rollback();
    }
}
