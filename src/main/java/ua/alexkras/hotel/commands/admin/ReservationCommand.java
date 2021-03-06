package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.admin.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.admin.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.commands.admin.reservation.SelectReservationCommand;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservationCommand implements Command {

    public static final String pathBasename = "reservation";

    private final ReservationService<Pageable> reservationService;
    private final ApartmentService<Pageable> apartmentService;

    private final Map<String,Command> commands = new HashMap<>();

    public ReservationCommand(ReservationService<Pageable> reservationService, ApartmentService<Pageable> apartmentService){
        commands.put(ConfirmReservationCommand.pathBasename,new ConfirmReservationCommand(reservationService));
        commands.put(CancelReservationCommand.pathBasename, new CancelReservationCommand(reservationService,apartmentService));
        commands.put(SelectReservationCommand.pathBasename, new SelectReservationCommand(reservationService,apartmentService));

        this.reservationService=reservationService;
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (commands.containsKey(command)) {
            return Optional.ofNullable(commands.get(command))
                    .orElseThrow(IllegalStateException::new)
                    .executeGet(request);
        }

        int reservationId = Integer.parseInt(Command.getCommand(request.getRequestURI(), pathBasename));

        Reservation reservation = reservationService.findById(reservationId).orElseThrow(IllegalStateException::new);
        request.setAttribute("reservation", reservation);

        if (!reservation.isCompleted()){
            String pageParam=request.getParameter("page");
            int page = (pageParam==null)?1:Integer.parseInt(pageParam);

            Pageable pageable = new Pageable(2,
                    apartmentService.getApartmentsMatchingReservationCount(reservation));
            pageable.seekToPage(page);

            List<Apartment> matchingApartments = apartmentService.findApartmentsMatchingReservation(reservation,pageable);
            request.getServletContext().log("Retrieved matching apartments from database...");

            request.setAttribute("pageable", pageable);
            request.setAttribute("matchingApartments",matchingApartments);
        }

        return "/WEB-INF/reservation/reservation.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "redirect:/"+ HotelServlet.pathBasename+"/"+ AdminCommand.pathBasename :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }
}
