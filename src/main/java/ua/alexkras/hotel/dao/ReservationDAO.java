package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class ReservationDAO {

    List<Reservation> findByUserId(int userId){
        return null;
    }

    List<Reservation> findByUserIdAndIsActive(int userId, boolean isActive){
        return null;
    }

    //@Query("update Reservation reservation set reservation.isActive =:isActive where reservation.userId =:userId")
    void updateActiveByUserId(int userId, boolean isActive){

    }

    Optional<Reservation> findById(int reservationId){
        return null;
    }

    List<Reservation> findByReservationStatus(ReservationStatus reservationStatus){
        return null;
    }

    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus where reservation.id =:id")
    void updateReservationStatusById(int id, ReservationStatus reservationStatus){

    }


    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus, " +
    //         "reservation.adminConfirmationDate =:confirmationDate where reservation.id =:id")
    void updateReservationStatusAndConfirmationDateById(
            int id, ReservationStatus reservationStatus, LocalDate confirmationDate){

    }
    /*
    @Query("update Reservation reservation set " +
            "reservation.apartmentId =:apartmentId, reservation.apartmentPrice =:apartmentPrice, " +
            "reservation.reservationStatus =:reservationStatus, " +
            "reservation.adminConfirmationDate =:confirmationDate  where reservation.id =:reservationId")

     */
    void updateApartmentIdAndPriceAndReservationStatusAndConfirmationDateById(
            int apartmentId,
            int apartmentPrice,
            ReservationStatus reservationStatus,
            LocalDate confirmationDate,
            int reservationId){

    }


    //@Query("update Reservation reservation set reservation.isPaid =:isPaid where reservation.id =:id")
    void updateIsPaidById(int id, boolean isPaid){

    }
}
