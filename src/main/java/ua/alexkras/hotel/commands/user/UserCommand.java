package ua.alexkras.hotel.commands.user;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserCommand implements Command {

    public static final String pathBasename = "user";

    private final Map<String, Command> commands = new HashMap<>();

    public UserCommand(){
        commands.put(CreateReservationCommand.pathBasename,new CreateReservationCommand(new ReservationService()));
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser.orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.USER)){
            return "redirect:/";
        }

        String command = Command.getCommand(request.getRequestURI(),pathBasename);


        return command.isEmpty() ?
                "/WEB-INF/personal_area/user.jsp" :
                Optional.ofNullable(commands.get(command))
                  .orElseThrow(IllegalStateException::new)
                  .executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/";
    }

}
