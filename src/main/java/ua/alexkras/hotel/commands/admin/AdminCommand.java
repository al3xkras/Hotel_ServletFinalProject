package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminCommand implements Command {

    public static final String pathBasename = "admin";
    private final ReservationService reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public AdminCommand(ApartmentService apartmentService, ReservationService reservationService){
        this.reservationService=reservationService;

        commands.put(AddApartmentCommand.pathBasename,new AddApartmentCommand(apartmentService));
        commands.put(ReservationCommand.pathBasename,new ReservationCommand(reservationService,apartmentService));
    }
    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.isPresent()){
            User testAdmin = User.builder()
                    .id(-200)
                    .userType(UserType.ADMIN)
                    .build();
            request.getSession().setAttribute("user", testAdmin);

            //return "redirect:/"+ HotelServlet.pathBasename +'/'+ LoginCommand.pathBasename;
        } else if (!currentUser.get().getUserType().equals(UserType.ADMIN)){
            return "redirect:/"+HotelServlet.pathBasename+'/';
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (command.isEmpty()){
            List<Reservation> reservations = reservationService.findByReservationStatus(
                    true,
                    ReservationStatus.PENDING,
                    1,50);

            request.setAttribute("pendingReservations",reservations);
            return "/WEB-INF/personal_area/admin.jsp";
        }

        return Optional.ofNullable(commands.get(command))
                 .orElseThrow(IllegalStateException::new)
                 .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        if (!AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new).getUserType().equals(UserType.ADMIN)){
            throw new RuntimeException();
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/admin.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }
}
