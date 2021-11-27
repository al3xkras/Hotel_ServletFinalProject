package ua.alexkras.hotel.commands.user.reservation;

import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ReservationService;
import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.model.ReservationStatus;

import javax.servlet.http.HttpServletRequest;



public class ConfirmReservationCommand implements Command {

    public static final String pathBasename = "confirm";

    private final ReservationService<Pageable> reservationService;

    public ConfirmReservationCommand(ReservationService<Pageable> reservationService){
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ UserCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

        reservationService.updateStatusById(reservationId, ReservationStatus.RESERVED);

        return "redirect:/"+HotelServlet.pathBasename+'/'+UserCommand.pathBasename;
    }
}
