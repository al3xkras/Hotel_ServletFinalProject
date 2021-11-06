package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationDAO extends GenericDao<Reservation> {
    Optional<Reservation> findById(long id);

    List<Reservation> findByUserId(long userId);

    List<Reservation> findByUserIdAndIsActive(long userId, boolean isActive);

    void updateActiveByUserId(long userId, boolean isActive);

    List<Reservation> findByReservationStatus(ReservationStatus reservationStatus, int start, int total);

    void updateReservationStatusById(long id, ReservationStatus reservationStatus);

    void updateReservationStatusAndConfirmationDateById(
            long id, ReservationStatus reservationStatus, LocalDate confirmationDate);

    void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long reservationId,
            long apartmentId,
            LocalDate confirmationDate);

    void updateIsPaidById(long id, boolean isPaid);

    List<Reservation> findAllByActiveAndStatus(boolean isActive,ReservationStatus reservationStatus, int start, int total);

    List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId,
            boolean isActive,
            ReservationStatus illegalStatus,
            int start, int total);
}
