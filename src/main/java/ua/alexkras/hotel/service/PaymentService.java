package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.impl.JDBCPaymentDao;
import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;

import java.util.Optional;

public class PaymentService {

    private final JDBCPaymentDao paymentDAO;
    private final ReservationService reservationService;

    private Optional<Reservation> currentPaymentReservation = Optional.empty();
    public void clearEverything(){
        clearCurrentPaymentReservation();
    }

    //@Autowired
    public PaymentService(JDBCPaymentDao paymentRepository,
                          ReservationService reservationService){
        this.paymentDAO =paymentRepository;
        this.reservationService=reservationService;
    }

    public void addPayment(Payment payment){
        //paymentDAO.save(payment);
    }

    /**
     * Updates Reservation, that is associated with current Payment by reservation id
     * -If current Payment Reservation is initialized, and
     *   Reservation's id is equal to @reservationId:
     *   return previously saved in memory Reservation
     * -Otherwise:
     *   Request Reservation from data source
     *
     * @param reservationId id of Reservation
     * @return newly updated (or existing) Reservation, that is associated with current payment
     * @throws IllegalStateException if Reservation with @reservationId was not found
     */
    public Reservation updateCurrentPaymentReservationByReservationId(int reservationId) {
        /*
        if (currentPaymentReservation.isPresent() && currentPaymentReservation.get().getId()==reservationId){
            return getCurrentPaymentReservation();
        }
        currentPaymentReservation = reservationService.getReservationById(reservationId);
        return getCurrentPaymentReservation();

         */
        return null;
    }

    public void clearCurrentPaymentReservation(){
        currentPaymentReservation=Optional.empty();
    }

    /**
     * @return Reservation, that is associated with current Payment by reservation id
     * @throws  IllegalStateException if current Payment Reservation is not present
     */
    public Reservation getCurrentPaymentReservation() {
        return currentPaymentReservation.orElseThrow(IllegalStateException::new);
    }
}
