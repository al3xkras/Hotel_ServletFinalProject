package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminCommand implements Command {

    public static final String pathBasename = "admin";
    private final ReservationServiceImpl reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public AdminCommand(ApartmentServiceImpl apartmentService, ReservationServiceImpl reservationService){
        this.reservationService=reservationService;

        commands.put(AddApartmentCommand.pathBasename,new AddApartmentCommand(apartmentService));
        commands.put(ReservationCommand.pathBasename,new ReservationCommand(reservationService,apartmentService));
    }
    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.isPresent()){
            //TODO remove in final build
            User testAdmin = User.builder()
                    .id(-2L)
                    .userType(UserType.ADMIN)
                    .build();
            request.getSession().setAttribute("user", testAdmin);
            //return "redirect:/"+ HotelServlet.pathBasename +'/'+ LoginCommand.pathBasename;
        } else if (!currentUser.get().getUserType().equals(UserType.ADMIN)){
            return "redirect:/"+HotelServlet.pathBasename+'/';
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (!command.isEmpty()){
            return Optional.ofNullable(commands.get(command))
                    .orElseThrow(CommandNotFoundException::new)
                    .executeGet(request);
        }

        String pageParam=request.getParameter("page");
        int page = (pageParam==null)?1:Integer.parseInt(pageParam);
        int reservationsCount = reservationService.getReservationsCountByActiveAndStatus(
                true,ReservationStatus.PENDING);

        request.getServletContext().log(""+reservationsCount);

        Pageable pageable = new Pageable(5,reservationsCount);
        pageable.seekToPage(page);

        request.getServletContext().log(pageable.toString());

        List<Reservation> reservations = reservationService.findByReservationStatus(
                true,
                ReservationStatus.PENDING,
                pageable);

        request.setAttribute("pageable",pageable);
        request.setAttribute("pendingReservations",reservations);
        return "/WEB-INF/personal_area/admin.jsp";
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
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }
}
