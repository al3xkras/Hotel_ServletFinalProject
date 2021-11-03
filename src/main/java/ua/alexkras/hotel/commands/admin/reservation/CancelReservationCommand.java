package ua.alexkras.hotel.commands.admin.reservation;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CancelReservationCommand implements Command {

    public static final String pathBasename = "cancel";

    private final ReservationService reservationService;
    private final ApartmentService apartmentService;

    public CancelReservationCommand(ReservationService reservationService,
                                    ApartmentService apartmentService){
        this.reservationService=reservationService;
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/"+ HotelServlet.pathBasename+'/'+ AdminCommand.pathBasename;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(), pathBasename);

        String delimiter = "&";

        int reservationId;
        Optional<Integer> apartmentId = Optional.empty();

        if (command.contains(delimiter)){
            String[] ints = command.split(delimiter,1);
            reservationId = Integer.parseInt(ints[0]);
            apartmentId = Optional.of(Integer.parseInt(ints[1]));
        } else {
            reservationId = Integer.parseInt(command);
        }

        reservationService.updateReservationStatusById(reservationId, ReservationStatus.CANCELLED);
        //TODO rollback connection if failed to update apartment status
        apartmentId.ifPresent(id->apartmentService.updateApartmentStatusById(id, ApartmentStatus.AVAILABLE));

        return "redirect:/"+HotelServlet.pathBasename+"/"+ AdminCommand.pathBasename;
    }

}
