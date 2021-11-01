package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;

import javax.servlet.http.HttpServletRequest;

public class SelectApartmentCommand implements Command {
    public static final String pathBasename = "apartment";

    private final ApartmentService apartmentService;

    public SelectApartmentCommand(ApartmentService apartmentService){
        this.apartmentService=apartmentService;
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        Integer apartmentId = Integer.valueOf(Command.getCommand(request.getRequestURI(),pathBasename));
        Apartment apartment = apartmentService.getApartmentById(apartmentId).orElseThrow(IllegalStateException::new);

        request.setAttribute("apartment", apartment);

        return "/WEB-INF/apartment/apartment.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/"+UserCommand.pathBasename;
    }
}
