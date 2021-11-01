package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ua.alexkras.hotel.model.mysql.ReservationTableStrings.selectPendingReservationsByUserIdWithLimit;

public class UserCommand implements Command {

    public static final String pathBasename = "user";

    private final ReservationService reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public UserCommand(ReservationService reservationService ){
        commands.put(CreateReservationCommand.pathBasename,new CreateReservationCommand(new ReservationService()));

        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        request.getServletContext().log(selectPendingReservationsByUserIdWithLimit);

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
        return "redirect:/";
    }

}
