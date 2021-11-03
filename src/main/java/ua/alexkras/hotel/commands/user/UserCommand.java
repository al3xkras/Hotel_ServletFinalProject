package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.user.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserCommand implements Command {

    public static final String pathBasename = "user";

    private final Map<String, Command> commands = new HashMap<>();

    private final ReservationService reservationService;

    public UserCommand(ReservationService reservationService, ApartmentService apartmentService){
        commands.put(CreateReservationCommand.pathBasename,new CreateReservationCommand(reservationService));
        commands.put(SelectReservationCommand.pathBasename,new SelectReservationCommand(reservationService,apartmentService));
        commands.put(CancelReservationCommand.pathBasename,new CancelReservationCommand(reservationService,apartmentService));
        commands.put(ConfirmReservationCommand.pathBasename,new ConfirmReservationCommand(reservationService));
        commands.put(SelectApartmentCommand.pathBasename,new SelectApartmentCommand(apartmentService,reservationService));

        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.USER)){
            return "redirect:/";
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (command.isEmpty()){
            List<Reservation> reservations = reservationService.getPendingReservationsByUserId(currentUser.get().getId(),1,5);
            request.setAttribute("reservations",reservations);
            return "/WEB-INF/personal_area/user.jsp";
        }

         return Optional.ofNullable(commands.get(command))
                  .orElseThrow(IllegalStateException::new)
                  .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/user.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }

}
