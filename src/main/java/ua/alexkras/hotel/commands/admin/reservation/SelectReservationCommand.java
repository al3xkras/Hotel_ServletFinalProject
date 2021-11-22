package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.dao.impl.ConnectionPoolHolder;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class SelectReservationCommand implements Command {

    public static final String pathBasename = "select";

    private final ReservationServiceImpl reservationService;
    private final ApartmentServiceImpl apartmentService;

    public SelectReservationCommand(ReservationServiceImpl reservationService,
                                    ApartmentServiceImpl apartmentService){
        this.reservationService=reservationService;
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+AdminCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(), pathBasename);

        String delimiter = "&";

        if (!command.contains(delimiter)) {
            throw new CommandNotFoundException();
        }

        String[] ints = command.split(delimiter,2);
        long reservationId = Long.parseLong(ints[0]);
        long apartmentId = Long.parseLong(ints[1]);

        request.getServletContext().log("id: "+reservationId+' '+apartmentId);

        Connection connection;
        try {
            connection = ConnectionPoolHolder.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            reservationService.transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(reservationId, apartmentId, LocalDate.now(),connection);
            apartmentService.transactionalUpdateApartmentStatusById(apartmentId, ApartmentStatus.RESERVED,connection);

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

        return "redirect:/"+HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

}
