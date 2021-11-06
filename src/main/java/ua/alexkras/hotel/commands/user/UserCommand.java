package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.user.reservation.CancelReservationCommand;
import ua.alexkras.hotel.commands.user.reservation.ConfirmReservationCommand;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//TODO replace RuntimeExceptions with custom exceptions
public class UserCommand implements Command {

    public static final String pathBasename = "user";

    private final Map<String, Command> commands;

    private final ReservationService reservationService;

    public UserCommand(ReservationService reservationService, ApartmentService apartmentService,
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
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();
        User user;

        if(!currentUser.isPresent()){
            //TODO remove in final build
            User testUser = User.builder()
                    .id(-100)
                    .userType(UserType.USER)
                    .build();
            request.getSession().setAttribute("user", testUser);
            currentUser=Optional.of(testUser);

            //return "redirect:/"+ HotelServlet.pathBasename +'/'+ LoginCommand.pathBasename;
        } else if (!currentUser.get().getUserType().equals(UserType.USER)){
            return "redirect:/"+HotelServlet.pathBasename+'/';
        }

        user=currentUser.orElseThrow(IllegalStateException::new);

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (command.isEmpty()){
            //TODO make pagination
            List<Reservation> reservations = reservationService
                    .findByUserIdAndActiveAndAnyStatusExcept(
                            user.getId(),
                            true,
                            ReservationStatus.CANCELLED,
                            1,50);

            request.setAttribute("reservations",reservations);
            return "/WEB-INF/personal_area/user.jsp";
        }

         return Optional.ofNullable(commands.get(command))
                  .orElseThrow(IllegalStateException::new)
                  .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        if (!AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new).getUserType().equals(UserType.USER)){
            throw new RuntimeException();
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/user.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }

}
