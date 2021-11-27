package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminCommand implements Command {

    public static final String pathBasename = "admin";
    private final ReservationService<Pageable> reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public AdminCommand(ApartmentService<Pageable> apartmentService,
                        ReservationService<Pageable> reservationService){
        this.reservationService=reservationService;

        commands.put(AddApartmentCommand.pathBasename,new AddApartmentCommand(apartmentService));
        commands.put(ReservationCommand.pathBasename,new ReservationCommand(reservationService,apartmentService));
    }
    @Override
    public String executeGet(HttpServletRequest request) {
        /*
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if (!currentUser.isPresent() || !currentUser.get().getUserType().equals(UserType.ADMIN)){
            throw new AccessDeniedException();
        }

         */

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

        //request.getServletContext().log(""+reservationsCount);

        Pageable pageable = new Pageable(5,reservationsCount);
        pageable.seekToPage(page);

        //request.getServletContext().log(pageable.toString());

        List<Reservation> reservations = reservationService.findAllByActiveAndStatus(
                true,
                ReservationStatus.PENDING,
                pageable);

        request.setAttribute("pageable",pageable);
        request.setAttribute("pendingReservations",reservations);
        return "/WEB-INF/personal_area/admin.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        /*
        if (!AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new).getUserType().equals(UserType.ADMIN)){
            throw new RuntimeException();
        }

         */

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/admin.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }
}
