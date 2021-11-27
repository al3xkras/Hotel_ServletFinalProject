package ua.alexkras.hotel.commands.user.reservation;

import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.dao.impl.ConnectionPoolHolder;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.model.ReservationStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;


public class CancelReservationCommand implements Command {

    public static final String pathBasename = "cancel";

    private final ReservationService<Pageable> reservationService;
    private final ApartmentService<Pageable> apartmentService;

    public CancelReservationCommand(ReservationService<Pageable> reservationService,
                                    ApartmentService<Pageable> apartmentService){
        this.reservationService=reservationService;
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ UserCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(), pathBasename);

        parseCommandAndCancelReservationAndUpdateApartmentStatus(
                command,
                reservationService, apartmentService,
                ReservationStatus.CANCELLED);

        return "redirect:/"+HotelServlet.pathBasename+"/"+ UserCommand.pathBasename;
    }

    public static void parseCommandAndCancelReservationAndUpdateApartmentStatus(
            String command,
            ReservationService<Pageable> reservationService,
            ApartmentService<Pageable> apartmentService,
            ReservationStatus cancelledStatus) {


        String delimiter = "&";

        int reservationId;
        Optional<Integer> apartmentId = Optional.empty();

        if (command.contains(delimiter)){
            String[] ints = command.split(delimiter,2);
            reservationId = Integer.parseInt(ints[0]);
            apartmentId = Optional.of(Integer.parseInt(ints[1]));
        } else {
            reservationId = Integer.parseInt(command);
        }

        Connection connection;
        try {
            connection = ConnectionPoolHolder.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        try {
            reservationService.transactionalUpdateStatusById(reservationId, cancelledStatus,connection);
            apartmentId.ifPresent(id -> apartmentService.transactionalUpdateApartmentStatusById(id, ApartmentStatus.AVAILABLE,connection));

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

    }

}
