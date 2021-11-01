package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminCommand implements Command {

    public static final String pathBasename = "admin";
    private final ReservationService reservationService;

    private final Map<String, Command> commands = new HashMap<>();

    public AdminCommand(ReservationService reservationService){
        this.reservationService=reservationService;

        commands.put(AddApartmentCommand.pathBasename,new AddApartmentCommand(new ApartmentService()));

    }
    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.ADMIN)){
            return "redirect:/";
        }

        //request.setAttribute("pendingReservations",reservationService.getCurrentPendingReservations());
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/admin.jsp" :
                Optional.ofNullable(commands.get(command))
                 .orElseThrow(IllegalStateException::new)
                 .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/personal_area/admin.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executePost(request);
    }

    /*
    @GetMapping
    public String adminMainPage(Model model){

        reservationService.updateCurrentPendingReservations();

        model.addAttribute("pendingReservations",
                reservationService.getCurrentPendingReservations());

        return "personal_area/admin";
    }

    @GetMapping("/reservation/{id}")
    public String pendingReservationPage(@PathVariable Integer id, Model model){
        reservationService.updateCurrentReservation(id);

        model.addAttribute("reservation",reservationService.getCurrentReservation());

        boolean isCompleted = reservationService.getCurrentReservation().isCompleted();
        model.addAttribute("isCompleted", isCompleted);

        if (!isCompleted) {
            model.addAttribute("matchingApartments", apartmentService.updateApartmentsMatchingCurrentReservation());
        }

        return "/reservation/reservation";
    }

    @GetMapping("/reservation/{id}/select/{apartmentId}")
    public String confirmReservationPage(@PathVariable("id") Integer reservationId,
                                         @PathVariable("apartmentId") Integer apartmentId,
                                         Model model){

        reservationService.updateCurrentReservation(reservationId);
        reservationService.getCurrentReservation().setApartmentId(apartmentId);

        model.addAttribute("isCompleted",false);
        model.addAttribute("reservation",reservationService.getCurrentReservation());
        model.addAttribute("matchingApartments",
                apartmentService.updateApartmentsMatchingCurrentReservation());
        model.addAttribute("apartmentSelected",true);

        return "/reservation/reservation";
    }

    @PostMapping("/reservation/{id}/confirm")
    public String confirmCompletedReservation(@PathVariable("id") Integer reservationId){

        reservationService.updateCurrentReservation(reservationId);
        if (!reservationService.getCurrentReservation().isCompleted())
            return "redirect:/error";
        reservationService.updateReservationStatusAndConfirmationDateById(
                reservationId,
                ReservationStatus.CONFIRMED,
                LocalDate.now());

        return "redirect:/";
    }

    @PostMapping("/reservation/{id}/confirm/{apartmentId}")
    public String confirmReservation(@PathVariable("id") Integer reservationId,
                                     @PathVariable("apartmentId") Integer apartmentId){

        apartmentService.updateCurrentApartment(apartmentId);
        reservationService.updateCurrentReservation(reservationId);

        if (!apartmentService.getCurrentApartment().matchesReservation(reservationService.getCurrentReservation())){
            return "redirect:/error";
        }

        reservationService.updateReservationWithApartmentById(reservationId, apartmentService.getCurrentApartment(), LocalDate.now());
        apartmentService.updateApartmentStatusById(apartmentId, ApartmentStatus.RESERVED);

        return "redirect:/";
    }

    @PostMapping("/reservation/{id}/cancel")
    public String dropReservation(@PathVariable("id") Integer reservationId){

        reservationService.updateCurrentReservation(reservationId);

        if (reservationService.getCurrentReservation().getApartmentId()!=null) {
            apartmentService.updateApartmentStatusById(
                    reservationService.getCurrentReservation().getApartmentId(),
                    ApartmentStatus.AVAILABLE);
        }

        reservationService.updateReservationStatusById(reservationId, ReservationStatus.CANCELLED);

        return "redirect:/";
    }

     */
}
