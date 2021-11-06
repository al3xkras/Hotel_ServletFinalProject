package ua.alexkras.hotel.commands.admin_or_user;

import ua.alexkras.hotel.commands.user.SelectApartmentCommand;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.exception.CommandNotFoundException;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ApartmentService;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ApartmentCommand implements Command{
    public static final String pathBasename = "apartments";

    private final ApartmentService apartmentService;
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, Comparator<Apartment>> sortBy = new HashMap<>();

    public ApartmentCommand(ApartmentService apartmentService, ReservationService reservationService){
        this.apartmentService=apartmentService;

        commands.put(SelectApartmentCommand.pathBasename,new SelectApartmentCommand(apartmentService,reservationService));

        sortBy.put("status", Comparator.comparing(Apartment::getStatus));
        sortBy.put("apartmentClass", Comparator.comparing(Apartment::getApartmentClass));
        sortBy.put("places", Comparator.comparing(Apartment::getPlaces).reversed());
        sortBy.put("price", Comparator.comparing(Apartment::getPrice).reversed());
    }

    @Override
    public String executeGet(HttpServletRequest request) {

        AuthFilter.getCurrentLoginUser().orElseThrow(IllegalStateException::new);

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        String pageParam=request.getParameter("page");
        String sort=request.getParameter("sort");

        int page = (pageParam==null)?1:Integer.parseInt(pageParam);
        sort = (sort==null || !sortBy.containsKey(sort))?"status":sort;

        Pageable pageable = new Pageable(apartmentService.getApartmentsCount());
        pageable.seekToPage(page);

        if (command.isEmpty()) {
            List<Apartment> apartments = apartmentService.findAllApartments(
                    pageable.getEntriesStart(),
                    pageable.getEntriesInPage());

            apartments.sort(sortBy.get(sort));

            request.setAttribute("pageable", pageable);
            request.setAttribute("apartments", apartments);
            return "/WEB-INF/apartment/apartments_menu.jsp";
        }

        return Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        return command.isEmpty() ?
                "/WEB-INF/apartment/apartments_menu.jsp" :
                Optional.ofNullable(commands.get(command))
                        .orElseThrow(CommandNotFoundException::new)
                        .executePost(request);
    }


}
