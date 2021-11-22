package ua.alexkras.hotel.service.impl;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCReservationDao;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ReservationService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService<Pageable,Reservation> {

    private final JDBCReservationDao reservationDAO;

    public static final long daysToCancelPayment = 2L;

    public ReservationServiceImpl(){
        this.reservationDAO = JDBCDaoFactory.getInstance().createReservationDAO();
    }

    /**
     * Add new reservation entity to a data source
     * @param reservation valid reservation to be created
     * @throws RuntimeException if @reservation is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    @Override
    public void create(Reservation reservation){
        reservationDAO.create(reservation);
    }

    /**
     * Add new reservation entity to a data source using transactional connection (without commit)
     * @param reservation valid reservation to be created
     * @throws RuntimeException if @reservation is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    public void createInTransaction(Reservation reservation, Connection connection){
        reservationDAO.createInTransaction(reservation, connection);
    }

    /**
     * Find reservation entity by id
     * @param id id of a reservation
     * @return Optional of reservation if present in a data source, Optional.empty() otherwise
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public Optional<Reservation> findById(long id){
        return reservationDAO.findById(id);
    }

    /**
     * Find all active/inactive reservations by status
     * @param isActive determines whether reservations in query are active/inactive
     * @param reservationStatus status of reservations in query
     * @param pageable Page, that limits amount of reservations to get from the data source
     * -retrieves a total of pageable.getEntriesInPage() entries, starting from pageable.getEntriesStart() item
     * @return List of reservations, that match conditions listed above
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public List<Reservation> findAllByActiveAndStatus(
            boolean isActive,
            ReservationStatus reservationStatus,
            Pageable pageable){

        return reservationDAO.findAllByActiveAndStatus(isActive,
                reservationStatus,pageable.getEntriesStart(), pageable.getEntriesInPage());
    }

    /**
     * Find all active/inactive reservations by user id, and any status except @illegalStatus, limited by @pageable
     * @param userId user id of all reservations in query
     * @param isActive determines whether reservations in query are active/inactive
     * @param illegalStatus status that reservations in query must not have
     * @param pageable Page, that limits amount of reservations to get from the data source
     * -retrieves a total of pageable.getEntriesInPage() entries, starting from pageable.getEntriesStart() item
     * @return List of reservations, that match conditions listed above
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId, boolean isActive, ReservationStatus illegalStatus, Pageable pageable){
        return  reservationDAO.findByUserIdAndActiveAndAnyStatusExcept(
                userId, isActive, illegalStatus, pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    /**
     * Get full cost of a @reservation
     * Full cost is calculated by formula:
     *  (reservation's apartment price for 1 day)*(date difference in days between reservation's "from date" and "to date")
     * @param reservation valid reservation ("from date","to date",all apartment-related columns are not-null)
     * @throws NullPointerException if @reservation is invalid
     * @return full cost of @reservation
     */
    @Override
    public int getReservationFullCost(Reservation reservation){
        return reservation.getApartmentPrice() *
                (int) Duration.between(reservation.getFromDate().atStartOfDay(),
                        reservation.getToDate().atStartOfDay()).toDays();
    }

    /**
     * Count reservations in a data source, that have specific user id, activity/inactivity status,
     * and any reservation status except @illegalStatus
     * @param userId user id
     * @param isActive determines whether reservations are active/inactive
     * @param illegalStatus status that reservations must not have
     * @return count of reservations, that match conditions listed above
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    public int getReservationsCountByUserIdAndActiveAndAnyStatusExcept(long userId, boolean isActive, ReservationStatus illegalStatus){
        return reservationDAO.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(userId,isActive,illegalStatus);
    }

    /**
     * Count reservations in a data source, that have specific activity/inactivity status, and reservation status
     * @param isActive determines whether reservations are active/inactive
     * @param status status that reservations must have
     * @return count of reservations, that match conditions listed above
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    public int getReservationsCountByActiveAndStatus(boolean isActive, ReservationStatus status){
        return reservationDAO.getReservationsCountByActiveAndStatus(isActive,status);
    }

    /**
     * Update Reservation status by id
     * @param id id of Reservation to be updated
     * @param reservationStatus reservation status after an update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void updateStatusById(long id, ReservationStatus reservationStatus){
        reservationDAO.updateReservationStatusById(id, reservationStatus);
    }

    /**
     * Update Reservation status by id using transactional connection (without commit)
     * @param id id of Reservation to be updated
     * @param reservationStatus reservation status after an update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    public void transactionalUpdateStatusById(long id, ReservationStatus reservationStatus, Connection connection){
        reservationDAO.transactionalUpdateReservationStatusById(id, reservationStatus, connection);
    }

    /**
     * Update Reservation status and confirmation date by id
     * @param id reservation id
     * @param status reservation status after update
     * @param confirmationDate confirmation date after an update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void updateStatusAndConfirmationDateById(long id, ReservationStatus status, LocalDate confirmationDate){
        reservationDAO.updateStatusAndConfirmationDateById(id,status,confirmationDate);
    }

    /**
     * Update Reservation's payment status by id
     * @param id reservation id
     * @param isPaid payment status after an update
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    @Override
    public void updateIsPaidById(long id, boolean isPaid){
        reservationDAO.updateIsPaidById(id,isPaid);
    }

    /**
     * Update Reservation's payment status by id using transactional connection (without commit)
     * @param id id of Reservation
     * @param isPaid new payment status
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    public void transactionalUpdateIsPaidById(long id, boolean isPaid, Connection connection){
        reservationDAO.transactionalUpdateIsPaidById(id,isPaid, connection);
    }

    /**
     * Update Reservation's associated apartment (apartment id, price), and
     * set status of reservation to 'Confirmed', and confirmation date
     * to @confirmationDate
     * @param id id of Reservation
     * @param apartmentId id of apartment to associate with Reservation
     * @param confirmationDate confirmation date by Admin
     */
    @Override
    public void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate){

        reservationDAO.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                id, apartmentId, confirmationDate);
    }

    /**
     * Update Reservation's associated apartment (apartment id, price), and
     * set status of reservation to 'Confirmed', and confirmation date
     * to @confirmationDate using transactional connection (without commit)
     * @param id id of Reservation
     * @param apartmentId id of apartment to associate with Reservation
     * @param confirmationDate confirmation date by Admin
     */
    public void transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate, Connection connection){
        reservationDAO.transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                id,apartmentId,confirmationDate, connection);
    }

    /**
     * Update all expired reservations (executed daily). During an update:
     * -Active reservations, that are expired, but not marked as 'expired' at the moment of execution, are updated
     * -Apartments (if they were associated with expired reservations) are marked as 'Available'
     * -All expired reservations are marked as 'inactive'
     * @throws RuntimeException if an SQLException was caught when executing update
     */
    public void updateAllExpiredReservations(Connection connection){
        try {
            reservationDAO.updateAllExpiredReservations(connection);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
