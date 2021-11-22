package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.dao.impl.ConnectionPoolHolder;
import ua.alexkras.hotel.entity.Payment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.impl.PaymentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MakePaymentCommand implements Command {
    public static final String pathBasename="make_payment";

    private final PaymentServiceImpl paymentService;
    private final ReservationServiceImpl reservationService;

    private Reservation reservation;

    public MakePaymentCommand(PaymentServiceImpl paymentService, ReservationServiceImpl reservationService) {
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        int reservationId = Integer.parseInt(command);

        reservation = reservationService.findById(reservationId)
                .orElseThrow(IllegalStateException::new);

        request.setAttribute("reservation",reservation);
        request.setAttribute("totalValue",reservationService.getReservationFullCost(reservation));

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
                    .value(reservationService.getReservationFullCost(reservation))
                    .reservationId(reservation.getId())
                    .build();

        } catch (NumberFormatException e){
            request.setAttribute("invalidCvv",true);
            return executeGet(request);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }



        Connection connection;
        try {
            connection = ConnectionPoolHolder.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            paymentService.create(payment, connection);
            reservationService.transactionalUpdateIsPaidById(payment.getReservationId(),true, connection);

            connection.commit();
            connection.close();
        } catch (Exception e){
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return "redirect:/"+ HotelServlet.pathBasename+'/'+UserCommand.pathBasename;
    }
}
