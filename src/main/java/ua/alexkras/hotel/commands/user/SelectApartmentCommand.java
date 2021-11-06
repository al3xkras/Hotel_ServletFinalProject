package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.dateFormat;

public class SelectApartmentCommand implements Command {
    public static final String pathBasename = "apartment";

    private final ApartmentService apartmentService;
    private final ReservationService reservationService;

    public SelectApartmentCommand(ApartmentService apartmentService,
                                  ReservationService reservationService){
        this.apartmentService=apartmentService;
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        Integer apartmentId = Integer.valueOf(Command.getCommand(request.getRequestURI(),pathBasename));
        Apartment apartment = apartmentService.getApartmentById(apartmentId).orElseThrow(IllegalStateException::new);

        request.setAttribute("apartment", apartment);

        return "/WEB-INF/apartment/apartment.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        User currentUser = AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new);

        Integer apartmentId = Integer.valueOf(Command.getCommand(request.getRequestURI(),pathBasename));
        Apartment apartment = apartmentService.getApartmentById(apartmentId).orElseThrow(IllegalStateException::new);

        if (!apartment.getStatus().equals(ApartmentStatus.AVAILABLE))
            throw new RuntimeException("Apartment is not available");

        LocalDate fromDate;
        LocalDate toDate;

        try {
            fromDate = dateFormat.parse(request.getParameter("fromDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            toDate = dateFormat.parse(request.getParameter("toDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e){
            throw new RuntimeException();
        }

        if (fromDate.compareTo(toDate)>=0) {
            request.setAttribute("fromDateIsGreaterThanToDate", true);
            request.setAttribute("apartment", apartment);
            return "/WEB-INF/apartment/apartment.jsp";
        }

        apartment.setStatus(ApartmentStatus.RESERVED);

        Reservation reservation = Reservation.builder()
                .userId(currentUser.getId())
                .apartmentId(apartment.getId())
                .apartmentClass(apartment.getApartmentClass())
                .places(apartment.getPlaces())
                .apartmentPrice(apartment.getPrice())
                .reservationStatus(ReservationStatus.PENDING)
                .fromDate(fromDate)
                .toDate(toDate)
                .submitDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .build();

        try {
            reservationService.transactionalAddReservation(reservation);
            apartmentService.transactionalUpdateApartment(apartment);

            reservationService.commitCurrentTransaction();
        } catch (Exception e){
            reservationService.rollbackConnection();
        }

        return "redirect:/app/"+UserCommand.pathBasename;
    }
}
