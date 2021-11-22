package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.dao.impl.ConnectionPoolHolder;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.dateFormat;

public class SelectApartmentCommand implements Command {
    public static final String pathBasename = "apartment";

    private final ApartmentServiceImpl apartmentService;
    private final ReservationServiceImpl reservationService;

    public SelectApartmentCommand(ApartmentServiceImpl apartmentService,
                                  ReservationServiceImpl reservationService){
        this.apartmentService=apartmentService;
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        int apartmentId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));
        Apartment apartment = apartmentService.findById(apartmentId).orElseThrow(IllegalStateException::new);

        request.setAttribute("apartment", apartment);

        return "/WEB-INF/apartment/apartment.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        User currentUser = AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new);

        int apartmentId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));
        Apartment apartment = apartmentService.findById(apartmentId).orElseThrow(IllegalStateException::new);

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

        Connection connection;
        try {
            connection = ConnectionPoolHolder.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            reservationService.createInTransaction(reservation,connection);
            apartmentService.transactionalUpdateApartment(apartment,connection);

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

        return "redirect:/app/"+UserCommand.pathBasename;
    }
}
