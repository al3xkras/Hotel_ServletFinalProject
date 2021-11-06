package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCReservationDao;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationService implements Service{

    private final JDBCReservationDao reservationDAO;

    private static final long daysToCancelPayment = 2L;

    public ReservationService(){
        this.reservationDAO = JDBCDaoFactory.getInstance().createReservationDAO();
    }

    public Optional<Reservation> getReservationById(long reservationId){
        return reservationDAO.findById(reservationId);
    }

    /**
     * Update Reservation status by id
     *
     * @param id id of Reservation to be updated
     * @param reservationStatus reservation status to assign to the Reservation
     */
    public void updateReservationStatusById(int id, ReservationStatus reservationStatus){
        reservationDAO.updateReservationStatusById(id, reservationStatus);
        reservationDAO.commit();
    }

    public void transactionalUpdateReservationStatusById(int id, ReservationStatus reservationStatus){
        reservationDAO.updateReservationStatusById(id, reservationStatus);
    }

    /**
     * Update Reservation status and date of confirmation by admin by Reservation's id
     *
     * @param id id of Reservation to be updated
     * @param reservationStatus new Reservation's status (after admins confirmation)
     * @param confirmationDate date of confirmation by Admin
     */
    public void updateReservationStatusAndConfirmationDateById(int id, ReservationStatus reservationStatus, LocalDate confirmationDate){
        /*
        reservationDAO.updateReservationStatusAndConfirmationDateById(id, reservationStatus, confirmationDate);
        clearCurrentReservation();
        clearCurrentUserActiveReservations();
        clearCurrentPendingReservations();

         */
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
    public void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate){

        reservationDAO.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                id,
                apartmentId,
                confirmationDate);
    }

    /**
     * Update Reservation's payment status by id
     * @param reservationId id of Reservation
     * @param isPaid new payment status
     */
    public void updatePaymentStatusById(long reservationId, boolean isPaid){
        reservationDAO.updateIsPaidById(reservationId,isPaid);
    }

    public void transactionalUpdatePaymentStatusById(long reservationId, boolean isPaid){
        reservationDAO.transactionalUpdateIsPaidById(reservationId,isPaid);
    }

    public List<Reservation> findByReservationStatus(
            boolean isActive,
            ReservationStatus reservationStatus,
            int start, int total){

        return reservationDAO.findAllByActiveAndStatus(isActive, reservationStatus,start, total);
    }

    public List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId,
            boolean isActive,
            ReservationStatus illegalStatus,
            int start, int total){

        return  reservationDAO.findByUserIdAndActiveAndAnyStatusExcept(
                userId,
                isActive,
                illegalStatus,
                start,total);
    }

    public void addReservation (Reservation reservation){
        reservationDAO.create(reservation);
    }

    public void transactionalAddReservation (Reservation reservation){
        reservationDAO.createInTransaction(reservation);
    }

    public int getTotalReservationValue(Reservation reservation){
        return reservation.getApartmentPrice()
                * (int) Duration.between(reservation.getFromDate().atStartOfDay(),
                    reservation.getToDate().atStartOfDay()).toDays();
    }

    public int getReservationsCountByUserIdAndActiveAndAnyStatusExcept(long userId, boolean isActive, ReservationStatus illegalStatus){
        return reservationDAO.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(userId,isActive,illegalStatus);
    }

    public int getReservationsCountByActiveAndStatus(boolean isActive, ReservationStatus status){
        return reservationDAO.getReservationsCountByActiveAndStatus(isActive,status);
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
