package ua.alexkras.hotel.commands.admin_or_user;

import ua.alexkras.hotel.commands.user.SelectApartmentCommand;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.service.ApartmentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ApartmentCommand implements Command{
    public static final String pathBasename = "apartments";

    private final ApartmentService apartmentService;
    private final Map<String, Command> commands = new HashMap<>();

    public ApartmentCommand(ApartmentService apartmentService){
        this.apartmentService=apartmentService;

        commands.put(SelectApartmentCommand.pathBasename,new SelectApartmentCommand(new ApartmentService()));
    }

    @Override
    public String executeGet(HttpServletRequest request) {

        AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new);

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        if (command.isEmpty()) {
            request.setAttribute("apartments", apartmentService.findApartments(1, 5));
            return "/WEB-INF/apartment/apartments_menu.jsp";
        }
        return Optional.ofNullable(commands.get(command))
                        .orElseThrow(IllegalStateException::new)
                        .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/";
    }


}
