package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.impl.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    public static final String pathBasename = "login";

    private final UserServiceImpl userService;
    public LoginCommand(UserServiceImpl userService){
        this.userService=userService;
    }
    @Override
    public String executeGet(HttpServletRequest request){

        String command = Command.getCommand(request.getRequestURI(),pathBasename);

        request.setAttribute("clear_session_storage",true);

        if (command.equals("redirect")){
            request.getServletContext().log("login: redirect");
            return "redirect:/app/login";
        }
        return "/login.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {

        String login = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = userService.findByUsername(login);

        if (!user.isPresent() || !password.equals(user.get().getPassword())){
            return "redirect:/app/login?error";
        }

        request.getSession().setAttribute("user",user.get());

        return user.get().getUserType().equals(UserType.USER)
                ?"redirect:/app/user"
                :"redirect:/app/admin";
    }
}
