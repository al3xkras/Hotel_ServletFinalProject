package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class MakePaymentCommand implements Command {
    public static final String pathBasename="make_payment";

    private final PaymentService paymentService;
    private final ReservationService reservationService;

    private Reservation reservation;

    public MakePaymentCommand(PaymentService paymentService, ReservationService reservationService) {
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        int reservationId = Integer.parseInt(command);

        reservation = reservationService.getReservationById(reservationId)
                .orElseThrow(IllegalStateException::new);

        request.setAttribute("reservation",reservation);

        return "/WEB-INF/personal_area/user/payment.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {

        String card_number = request.getParameter("cardNumber");
        String cvv = request.getParameter("cardCvv");
        String expirationDate = request.getParameter("expirationDate");

        Payment payment;

        try {
            payment = paymentService.paymentOf(card_number,cvv,expirationDate)
                    .paymentDate(LocalDateTime.now())
                    .userId(reservation.getUserId())
                    .value(reservation.getApartmentPrice())
                    .reservationId(reservation.getId())
                    .build();

            request.getServletContext().log(payment.toString());

        } catch (NumberFormatException e){
            request.setAttribute("invalidCvv",true);
            return executeGet(request);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return "redirect:/";
    }
}
