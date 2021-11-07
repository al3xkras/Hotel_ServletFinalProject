package ua.alexkras.hotel.commands.admin;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class AddApartmentCommand implements Command {
    public static final String pathBasename = "add_apartment";

    private final ApartmentServiceImpl apartmentService;

    public AddApartmentCommand(ApartmentServiceImpl apartmentService){
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return "/WEB-INF/apartment/add_apartment.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {

        Apartment apartment = Apartment.builder()
                .name(request.getParameter("name"))
                .places(Integer.parseInt(request.getParameter("places")))
                .apartmentClass(ApartmentClass.valueOf(request.getParameter("apartmentClass")))
                .status(ApartmentStatus.valueOf(request.getParameter("status")))
                .price(Integer.parseInt(request.getParameter("price")))
                .build();

        apartmentService.create(apartment);

        request.getServletContext().log("Added apartment: "+apartment);

        return "redirect:/"+ HotelServlet.pathBasename+'/'+AdminCommand.pathBasename;
    }
}
