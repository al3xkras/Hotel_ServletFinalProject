package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.user.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.AccessDeniedException;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UserCommand implements Command {

    public static final String pathBasename = "user";

    private final Map<String, Command> commands;

    private final ReservationService<Pageable> reservationService;

    public UserCommand(ReservationService<Pageable> reservationService, ApartmentService<Pageable> apartmentService,
                       PaymentService paymentService){
        HashMap<String,Command> commands = new HashMap<>();

        commands.put(CreateReservationCommand.pathBasename,new CreateReservationCommand(reservationService));
        commands.put(ReservationCommand.pathBasename,new ReservationCommand(reservationService,apartmentService));
        commands.put(CancelReservationCommand.pathBasename,new CancelReservationCommand(reservationService,apartmentService));
        commands.put(ConfirmReservationCommand.pathBasename,new ConfirmReservationCommand(reservationService));
        commands.put(SelectApartmentCommand.pathBasename,new SelectApartmentCommand(apartmentService,reservationService));
        commands.put(MakePaymentCommand.pathBasename,new MakePaymentCommand(paymentService, reservationService));

        this.commands= Collections.unmodifiableMap(commands);
        this.reservationService=reservationService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        //Optional<User> currentUser = AuthFilter.getCurrentLoginUser();
        User user = AuthFilter.getCurrentLoginUser().orElseThrow(AccessDeniedException::new);

        /*
        if (!currentUser.isPresent() || !currentUser.get().getUserType().equals(UserType.USER)){
            throw new AccessDeniedException();
        }
         */

        String command = Command.getCommand(request.getRequestURI(),pathBasename);
        if (!command.isEmpty()){
            return Optional.ofNullable(commands.get(command))
                    .orElseThrow(CommandNotFoundException::new)
                    .executeGet(request);
        }

        //user=currentUser.get();

        String pageParam=request.getParameter("page");
        int page = (pageParam==null)?1:Integer.parseInt(pageParam);
        int reservationsCount = reservationService.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
                user.getId(),true,ReservationStatus.CANCELLED);

        Pageable pageable = new Pageable(10,reservationsCount);
        pageable.seekToPage(page);

        List<Reservation> reservations = reservationService
                .findByUserIdAndActiveAndAnyStatusExcept(
                        user.getId(),
                        true,
                        ReservationStatus.CANCELLED,
                        pageable);

        reservations.stream()
                .map(Object::toString)
                .forEach(request.getServletContext()::log);

        request.setAttribute("pageable", pageable);
        request.setAttribute("reservations",reservations);
        return "/WEB-INF/personal_area/user.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        /*
        if (!AuthFilter.getCurrentLoginUser().orElseThrow(UserNotFoundException::new).getUserType().equals(UserType.USER)){
            throw new AccessDeniedException();
        }
        */

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/user.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }

}
