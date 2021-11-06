package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class SelectReservationCommand implements Command {

    public static final String pathBasename = "select";

    private final ReservationService reservationService;
    private final ApartmentService apartmentService;

    public SelectReservationCommand(ReservationService reservationService,
                                    ApartmentService apartmentService){
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

        reservationService.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(reservationId,apartmentId,LocalDate.now());
        //TODO rollback connection if failed to update apartment's status
        apartmentService.updateApartmentStatusById(apartmentId, ApartmentStatus.RESERVED);


        return "redirect:/"+HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

}
