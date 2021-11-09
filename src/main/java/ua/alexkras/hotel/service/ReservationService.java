package ua.alexkras.hotel.service;

import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService<Pageable,Entity>
        extends Service<Pageable,Entity>{

    void createInTransaction(Entity entity);

    List<Entity> findAllByActiveAndStatus(
            boolean isActive,
            ReservationStatus reservationStatus,
            Pageable pageable);

    List<Entity> findByUserIdAndActiveAndAnyStatusExcept(
            long userId, boolean isActive,
            ReservationStatus illegalStatus, Pageable pageable);

    int getReservationFullCost(Reservation reservation);

    void updateStatusById(long id, ReservationStatus reservationStatus);
    void updateStatusAndConfirmationDateById(long id, ReservationStatus status, LocalDate confirmationDate);

    void transactionalUpdateStatusById(long id, ReservationStatus reservationStatus);

    void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate);
    void transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long id, long apartmentId, LocalDate confirmationDate);

    void updateIsPaidById(long reservationId, boolean isPaid);

    void transactionalUpdateIsPaidById(long reservationId, boolean isPaid);

    void updateAllExpiredReservations();

}
