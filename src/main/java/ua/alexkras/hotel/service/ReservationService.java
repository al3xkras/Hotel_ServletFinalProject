package ua.alexkras.hotel.service;

import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService<Pageable> extends Service<Reservation>{

    List<Reservation> findAllByActiveAndStatus(
            boolean isActive,
            ReservationStatus reservationStatus,
            Pageable pageable);

    List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId, boolean isActive,
            ReservationStatus illegalStatus, Pageable pageable);

    int getReservationFullCost(Reservation reservation);

    void createInTransaction(Reservation reservation, Connection connection);

    void updateStatusById(long id, ReservationStatus reservationStatus);

    void updateStatusAndConfirmationDateById(long id, ReservationStatus status, LocalDate confirmationDate);

    void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate);

    void updateIsPaidById(long reservationId, boolean isPaid);

    int getReservationsCountByUserIdAndActiveAndAnyStatusExcept(long userId, boolean isActive, ReservationStatus illegalStatus);

    int getReservationsCountByActiveAndStatus(boolean isActive, ReservationStatus status);

    void transactionalUpdateStatusById(long id, ReservationStatus reservationStatus, Connection connection);

    void transactionalUpdateIsPaidById(long id, boolean isPaid, Connection connection);

    void transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate, Connection connection);

    void updateAllExpiredReservations(Connection connection);
}
