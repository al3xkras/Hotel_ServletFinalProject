package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.ReservationService;

//@RequestMapping("/user")
public class UserController {

    private final AuthController authController;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final ApartmentService apartmentService;



    //@Autowired
    public UserController(AuthController authController,
                          ReservationService reservationService,
                          PaymentService paymentService,
                          ApartmentService apartmentService){
        this.authController=authController;
        this.reservationService=reservationService;
        this.paymentService=paymentService;
        this.apartmentService=apartmentService;
    }

    /*

    @GetMapping
    public String userMainPage(Model model){
        Optional<User> optionalUser = authController.getCurrentUser();

        if (!optionalUser.isPresent()){
            return "index";
        }

        model.addAttribute("allReservations",
                reservationService.updateCurrentUserActiveReservationsById(optionalUser.get().getId())
        );

        return "personal_area/user";
    }


    @GetMapping("/reservation/{id}")
    public String reservationFromTable(@PathVariable("id") Integer reservationId,
                                       Model model){

        Reservation currentReservation = reservationService.updateCurrentReservation(reservationId);

        if (!currentReservation.isCompleted()){
            return "redirect:/";
        }

        model.addAttribute("reservation",currentReservation);
        model.addAttribute("isCompleted",true);
        model.addAttribute("userAccount",true);

        return "/reservation/reservation";
    }


    @PostMapping("/reservation/{id}/confirm")
    public String confirmReservation(@PathVariable("id") Integer reservationId){

        Reservation currentReservation = reservationService.updateCurrentReservation(reservationId);

        if (currentReservation.getUserId()!=
                authController.getCurrentUser().orElseThrow(IllegalStateException::new).getId() ||
                currentReservation.isExpired()){
            return "redirect:/";
        }

        reservationService.updateReservationStatusById(reservationId,ReservationStatus.RESERVED);

        return "redirect:/";
    }


    @PostMapping("/reservation/{id}/cancel")
    public String cancelReservation(@PathVariable("id") Integer reservationId) {

        Reservation currentReservation = reservationService.updateCurrentReservation(reservationId);

        if (currentReservation.getUserId()!= authController
                .getCurrentUser().orElseThrow(IllegalStateException::new)
                .getId() ||
                currentReservation.isExpired()){
            return "redirect:/";
        }

        apartmentService.updateApartmentStatusById(
                currentReservation.getApartmentId(),
                ApartmentStatus.AVAILABLE);

        reservationService.updateReservationStatusById(
                reservationId,
                ReservationStatus.CANCELLED);

        return "redirect:/";
    }


    @GetMapping("/reservation/{id}/make_payment")
    public String makePaymentPage(@PathVariable("id") Integer reservationId,
                                  Model model){
        User user = authController.getCurrentUser().orElseThrow(IllegalStateException::new);
        Reservation currentPaymentReservation = paymentService.updateCurrentPaymentReservationByReservationId(reservationId);

        Payment payment = new Payment();
        payment.setReservationId(reservationId);
        payment.setUserId(user.getId());
        payment.setValue(currentPaymentReservation.getApartmentPrice());

        model.addAttribute("payment",payment);

        return "/personal_area/user/payment";
    }


    @PostMapping("/reservation/{id}/make_payment")
    public String makePayment(@PathVariable("id") Integer reservationId,
                              @ModelAttribute("payment") Payment payment,
                              Model model){
        User user = authController.getCurrentUser().orElseThrow(IllegalStateException::new);

        Reservation currentPaymentReservation = paymentService.updateCurrentPaymentReservationByReservationId(reservationId);

        if (!payment.getCardCvv().matches("^(\\d{3})$")){
            payment.setReservationId(reservationId);
            payment.setUserId(user.getId());
            payment.setValue(currentPaymentReservation.getApartmentPrice());

            model.addAttribute("invalidCvv",true);
            return "/personal_area/user/payment";
        }

        reservationService.updateCurrentReservation(reservationId);

        if (user.getId()!= reservationService.getCurrentReservation().getUserId()){
            return "redirect:/";
        }

        payment.setUserId(reservationService
                .getCurrentReservation().getUserId());
        payment.setReservationId(reservationService
                .getCurrentReservation().getId());
        payment.setValue(reservationService
                .getCurrentReservation().getApartmentPrice());
        payment.setPaymentDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        paymentService.addPayment(payment);
        reservationService.updateReservationPaymentStatusById(reservationId,true);

        return "redirect:/";
    }

     */

}
