package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.ReservationDAO;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class JDBCReservationDao implements ReservationDAO {

    private final Connection connection;

    public JDBCReservationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Reservation> findByUserId(long userId){
        return null;
    }

    @Override
    public List<Reservation> findByUserIdAndIsActive(long userId, boolean isActive){
        return null;
    }

    //@Query("update Reservation reservation set reservation.isActive =:isActive where reservation.userId =:userId")
    @Override
    public void updateActiveByUserId(long userId, boolean isActive){

    }

    @Override
    public boolean create(Reservation reservation) {
        if (reservation.getId()!=null){
            return create(reservation.getId(),reservation);
        }
        return false;
    }

    @Override
    public boolean create(long id, Reservation reservation){
        try(PreparedStatement addApartment = connection.prepareStatement("")
        ){
            addApartment.setLong(1,id);

            addApartment.execute();
        } catch (SQLIntegrityConstraintViolationException ignored){

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Optional<Reservation> findById(long reservationId){
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public void update(Reservation entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }

    @Override
    public List<Reservation> findByReservationStatus(ReservationStatus reservationStatus){
        return null;
    }

    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus where reservation.id =:id")
    @Override
    public void updateReservationStatusById(long id, ReservationStatus reservationStatus){

    }


    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus, " +
    //         "reservation.adminConfirmationDate =:confirmationDate where reservation.id =:id")
    @Override
    public void updateReservationStatusAndConfirmationDateById(
            long id, ReservationStatus reservationStatus, LocalDate confirmationDate){

    }
    /*
    @Query("update Reservation reservation set " +
            "reservation.apartmentId =:apartmentId, reservation.apartmentPrice =:apartmentPrice, " +
            "reservation.reservationStatus =:reservationStatus, " +
            "reservation.adminConfirmationDate =:confirmationDate  where reservation.id =:reservationId")

     */
    @Override
    public void updateApartmentIdAndPriceAndReservationStatusAndConfirmationDateById(
            long apartmentId,
            int apartmentPrice,
            ReservationStatus reservationStatus,
            LocalDate confirmationDate,
            int reservationId){

    }


    //@Query("update Reservation reservation set reservation.isPaid =:isPaid where reservation.id =:id")
    @Override
    public void updateIsPaidById(long id, boolean isPaid){

    }
}
