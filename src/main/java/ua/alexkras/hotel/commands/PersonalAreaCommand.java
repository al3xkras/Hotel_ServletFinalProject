package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class PersonalAreaCommand implements Command {

    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        return currentUser
                .orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.USER)
                ?"/WEB-INF/personal_area/user.jsp"
                :"/WEB-INF/personal_area/admin.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "/";
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
