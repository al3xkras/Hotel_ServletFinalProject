package ua.alexkras.hotel.service.impl;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCReservationDao;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ReservationService;

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

    @Override
    public void create(Reservation reservation){
        reservationDAO.create(reservation);
    }

    @Override
    public void createInTransaction(Reservation reservation){
        reservationDAO.createInTransaction(reservation);
    }

    @Override
    public Optional<Reservation> findById(long reservationId){
        return reservationDAO.findById(reservationId);
    }

    @Override
    public List<Reservation> findByReservationStatus(
            boolean isActive,
            ReservationStatus reservationStatus,
            Pageable pageable){

        return reservationDAO.findAllByActiveAndStatus(isActive,
                reservationStatus,pageable.getEntriesStart(), pageable.getEntriesInPage());
    }

    @Override
    public List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId, boolean isActive, ReservationStatus illegalStatus, Pageable pageable){
        return  reservationDAO.findByUserIdAndActiveAndAnyStatusExcept(
                userId, isActive, illegalStatus, pageable.getEntriesStart(),pageable.getEntriesInPage());
    }

    @Override
    public int getReservationFullCost(Reservation reservation){
        return reservation.getApartmentPrice() *
                (int) Duration.between(reservation.getFromDate().atStartOfDay(),
                        reservation.getToDate().atStartOfDay()).toDays();
    }

    public int getReservationsCountByUserIdAndActiveAndAnyStatusExcept(long userId, boolean isActive, ReservationStatus illegalStatus){
        return reservationDAO.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(userId,isActive,illegalStatus);
    }

    public int getReservationsCountByActiveAndStatus(boolean isActive, ReservationStatus status){
        return reservationDAO.getReservationsCountByActiveAndStatus(isActive,status);
    }

    /**
     * Update Reservation status by id
     *
     * @param id id of Reservation to be updated
     * @param reservationStatus reservation status to assign to the Reservation
     */
    @Override
    public void updateStatusById(int id, ReservationStatus reservationStatus){
        reservationDAO.transactionalUpdateReservationStatusById(id, reservationStatus);
        reservationDAO.commit();
    }

    @Override
    public void updateStatusAndConfirmationDateById(int id, ReservationStatus status, LocalDate confirmationDate){
        reservationDAO.updateStatusAndConfirmationDateById(id,status,confirmationDate);
    }

    @Override
    public void transactionalUpdateStatusById(int id, ReservationStatus reservationStatus){
        reservationDAO.transactionalUpdateReservationStatusById(id, reservationStatus);
    }

    /**
     * Update Reservation's payment status by id
     * @param reservationId id of Reservation
     * @param isPaid new payment status
     */
    @Override
    public void updateIsPaidById(long reservationId, boolean isPaid){
        reservationDAO.updateIsPaidById(reservationId,isPaid);
    }

    @Override
    public void transactionalUpdateIsPaidById(long reservationId, boolean isPaid){
        reservationDAO.transactionalUpdateIsPaidById(reservationId,isPaid);
    }

    /**
     * Update Reservation's associated apartment (apartment id, price), and
     * set status of reservation to 'Confirmed', and set confirmation date
     * to @confirmationDate
     *
     * @param id id of Reservation
     * @param apartmentId id of apartment to associate with Reservation
     * @param confirmationDate date of confirmation by Admin
     */
    @Override
    public void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate){

        reservationDAO.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                id, apartmentId, confirmationDate);
    }

    /**
     * Update Reservation's associated apartment (apartment id, price), and
     * set status of reservation to 'Confirmed', and set confirmation date
     * to @confirmationDate
     *
     * @param id id of Reservation
     * @param apartmentId id of apartment to associate with Reservation
     * @param confirmationDate date of confirmation by Admin
     */
    @Override
    public void transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate){
        reservationDAO.transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                id,apartmentId,confirmationDate);
    }

    @Override
    public void updateAllExpiredReservations(){
        reservationDAO.updateAllExpiredReservations();
    }

    @Override
    public void commitCurrentTransaction(){
        reservationDAO.commit();
    }

    @Override
    public void rollbackConnection(){
        reservationDAO.rollback();
    }
}