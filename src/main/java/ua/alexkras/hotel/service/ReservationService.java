package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCReservationDao;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationService {

    private final JDBCReservationDao reservationDAO;

    private static final long daysToCancelPayment = 2L;

    public ReservationService(){
        this.reservationDAO = JDBCDaoFactory.getInstance().createReservationDAO();
    }

    /*
    public boolean updateAllExpiredReservations(){
        try (Connection conn = DriverManager.getConnection(MySqlStrings.root, MySqlStrings.user, MySqlStrings.password);
             PreparedStatement updateExpired = conn.prepareStatement("UPDATE " +
                     "hotel_db.reservations SET " +
                     "status=?,"+
                     "expired=true "+
                     "WHERE not expired and not is_paid and " +
                     "admin_confirmation_date is not null and " +
                     "DATEDIFF(admin_confirmation_date,?)>=?");
             PreparedStatement setExpiredReservationApartmentsAvailable = conn.prepareStatement("UPDATE " +
                     "hotel_db.apartments SET "+
                     "apartment_status=? "+
                     "WHERE apartment_status='"+ApartmentStatus.RESERVED+"' and "+
                     "id IN (SELECT apartment_id FROM hotel_db.reservations WHERE expired and is_active)");
             PreparedStatement updateActive = conn.prepareStatement("UPDATE " +
                     "hotel_db.reservations SET " +
                     "is_active=false "+
                     "WHERE is_active and expired ")
             ){
            updateExpired.setString(1,ReservationStatus.CANCELLED.name());
            updateExpired.setDate(2, Date.valueOf(LocalDate.now()));
            updateExpired.setLong(3,daysToCancelPayment);
            updateExpired.executeUpdate();

            setExpiredReservationApartmentsAvailable.setString(1,ApartmentStatus.AVAILABLE.name());
            setExpiredReservationApartmentsAvailable.executeUpdate();

            updateActive.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Reservation> getReservationsByUserId(int userId){
        return reservationDAO.findByUserId(userId);
    }

    public List<Reservation> getActiveReservationsByUserId(int userId){
        return reservationDAO.findByUserIdAndIsActive(userId,true);
    }



    public List<Reservation> getAllReservations(){
        return reservationDAO.findAll();
    }

    public List<Reservation> getPendingReservations(){
        return reservationDAO.findByReservationStatus(ReservationStatus.PENDING);
    }

     */

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
     * @param apartment apartment to associate with Reservation
     * @param confirmationDate date of confirmation by Admin
     */
    public void updateReservationWithApartmentById(int id, Apartment apartment, LocalDate confirmationDate){
        /*
        reservationDAO.updateApartmentIdAndPriceAndReservationStatusAndConfirmationDateById(
                apartment.getId(),
                apartment.getPrice(),
                ReservationStatus.CONFIRMED,
                confirmationDate,
                id);

        clearCurrentReservation();
        clearCurrentUserActiveReservations();
        clearCurrentPendingReservations();

         */
    }

    /**
     * Update Reservation's payment status by id
     * @param reservationId id of Reservation
     * @param isPaid new payment status
     */
    public void updateReservationPaymentStatusById(int reservationId, boolean isPaid){
        /*
        reservationDAO.updateIsPaidById(reservationId,isPaid);
        clearCurrentReservation();
        clearCurrentUserActiveReservations();
        clearCurrentPendingReservations();

         */
    }


    /**
     * Update reservation's days until expiration
     * -If reservation is not confirmed by admin:
     *   -Do nothing
     *
     * -Otherwise:
     *   -Calculate days between confirmation date and today
     *   -Update Reservation's days until expiration
     * @param reservation Reservation that will be updated
     * @return Reservation with updated days until expiration,
     *   if it was confirmed by admin. Otherwise, returns @reservation
     */
    private Reservation updateReservationDaysUntilExpiration(Reservation reservation){
        /*
        if (reservation.getAdminConfirmationDate()==null){
            return reservation;
        }
        LocalDate submitDate=reservation.getAdminConfirmationDate();
        long daysBetween = DAYS.between(LocalDate.now(),submitDate);
        reservation.setDaysUntilExpiration(daysToCancelPayment-daysBetween);
        return reservation;

         */
        return null;
    }

    public List<Reservation> getPendingReservations(int start, int total){
        return reservationDAO.findAllPendingReservations(start, total);
    }

    public List<Reservation> getPendingReservationsByUserId(long userId,int start, int total){
        return  reservationDAO.findPendingReservationsByUserId(userId,start,total);
    }

    public boolean addReservation (Reservation reservation){
        return reservationDAO.create(reservation);
    }
}
