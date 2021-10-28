package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.PaymentService;
import ua.alexkras.hotel.service.ReservationService;

public class CustomLogoutSuccessHandler {

    private final AuthController authController;
    private final ApartmentService apartmentService;
    private final PaymentService paymentService;
    private final ReservationService reservationService;


    //@Autowired
    public CustomLogoutSuccessHandler(ApartmentService apartmentService,
                                      PaymentService paymentService,
                                      ReservationService reservationService,
                                      AuthController authController){
        this.apartmentService=apartmentService;
        this.paymentService=paymentService;
        this.reservationService=reservationService;
        this.authController=authController;
    }

    //@Override
    /*
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        HotelUserDetailsService.clearCurrentUserDetails();
        authController.clearCurrentUser();

        apartmentService.clearEverything();
        reservationService.clearEverything();
        paymentService.clearEverything();

        super.onLogoutSuccess(request, response, authentication);
    }

     */
}
