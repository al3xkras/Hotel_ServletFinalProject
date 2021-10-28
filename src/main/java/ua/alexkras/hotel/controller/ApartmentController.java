package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

public class ApartmentController {

    private final ApartmentService apartmentService;
    private final AuthController authController;
    private final ReservationService reservationService;

    //@Autowired
    public ApartmentController(ApartmentService apartmentService,
                               ReservationService reservationService,
                               AuthController authController){
        this.apartmentService=apartmentService;
        this.authController=authController;
        this.reservationService=reservationService;
    }

    //@GetMapping("/apartments")
    /*
    public String listApartments(@RequestParam(value = "sort",required=false) String by,
                                 @RequestParam("page") Optional<Integer> page,
                                 Model model){

        by = by==null?"price":by;

        Pageable currentPage = PageRequest.of(page.orElse(0), 2, Sort.by(by));


        Page<Apartment> apartments = apartmentService.getAllApartments(currentPage);

        model.addAttribute("allApartments", apartments);
        model.addAttribute("userAccount", authController.getCurrentUser()
                .orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.USER));

        log.info("Page size: "+currentPage.getPageSize()+
                " Page number: "+currentPage.getPageNumber()+
                " Total pages: "+apartments.getTotalPages());

        return "/apartment/apartments_menu";
    }

    @GetMapping("/add_apartment")
    public String apartmentAddPage(Model model){
        model.addAttribute("apartment",new Apartment());

        return "/apartment/add_apartment";
    }

    @PostMapping("/add_apartment")
    public String onApartmentAdd(
            @ModelAttribute("apartment") Apartment apartment){

        apartmentService.addApartment(apartment);

        return "redirect:/";
    }

    @GetMapping("/apartment/{id}")
    public String apartmentPage(@PathVariable("id") Integer id,
                                Model model){

        Apartment currentApartment = apartmentService.updateCurrentApartment(id);

        model.addAttribute("apartment",currentApartment);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("userAccount", authController.getCurrentUser()
                .orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.USER));

        return "/apartment/apartment";
    }

    @PostMapping("/apartment/{id}")
    public String submitExistingApartmentReservation(@PathVariable("id") Integer id,
                                                     @ModelAttribute("reservation") Reservation reservationDate,
                                                     Model model){

        if (reservationDate.getFromDate().compareTo(reservationDate.getToDate())>=0){
            model.addAttribute("apartment",apartmentService.updateCurrentApartment(id));
            model.addAttribute("fromDateIsGreaterThanToDate",true);
            return "/apartment/apartment";
        }

        User currentUser = authController.getCurrentUser().orElseThrow(IllegalStateException::new);
        Apartment apartment = apartmentService.updateCurrentApartment(id);

        if (!apartment.getStatus().equals(ApartmentStatus.AVAILABLE) ||
                !currentUser.getUserType().equals(UserType.USER)){
            return "redirect:/apartments";
        }

        Reservation reservation = new Reservation();
        reservation.setUserId(currentUser.getId());
        reservation.setApartmentClass(apartment.getApartmentClass());
        reservation.setFromDate(reservationDate.getFromDate());
        reservation.setToDate(reservationDate.getToDate());
        reservation.setSubmitDate(LocalDateTime.now());
        reservation.setPlaces(apartment.getPlaces());
        reservation.setApartmentId(apartment.getId());
        reservation.setApartmentPrice(apartment.getPrice());
        reservation.setReservationStatus(ReservationStatus.PENDING);

        apartmentService.updateApartmentStatusById(apartment.getId(),ApartmentStatus.RESERVED);
        reservationService.addReservation(reservation);
        return "redirect:/";
    }

     */



}
