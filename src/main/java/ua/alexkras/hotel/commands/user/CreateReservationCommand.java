package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.model.mysql.MySqlStrings;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class CreateReservationCommand implements Command {

    public static final String pathBasename = "create_reservation";

    private final ReservationService reservationService;

    public CreateReservationCommand(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "/WEB-INF/reservation/create_reservation.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        User user = AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new);

        LocalDate fromDate;
        LocalDate toDate;

        try {
            fromDate = MySqlStrings.dateFormat.parse(request.getParameter("fromDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            toDate = MySqlStrings.dateFormat.parse(request.getParameter("toDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e){
            throw new IllegalStateException();
        }

        if (fromDate.compareTo(toDate)>=0) {
            request.setAttribute("fromDateIsGreaterThanToDate", true);
            return executeGet(request);
        }

        Reservation reservation = Reservation.builder()
                .userId(user.getId())
                .apartmentClass(ApartmentClass.valueOf(request.getParameter("apartmentClass")))
                .places(Integer.parseInt(request.getParameter("places")))
                .reservationStatus(ReservationStatus.PENDING)
                .fromDate(fromDate)
                .toDate(toDate)
                .submitDate(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS))
                .build();

        reservationService.addReservation(reservation);

        return "redirect:/"+ HotelServlet.pathBasename+'/'+UserCommand.pathBasename;

    }
}
