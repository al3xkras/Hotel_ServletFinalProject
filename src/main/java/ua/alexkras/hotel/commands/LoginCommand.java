package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    public static final String pathBasename = "login";

    private final UserService userService;
    public LoginCommand(UserService userService){
        this.userService=userService;
    }
    @Override
    public String executeGet(HttpServletRequest request){
        request.getServletContext().log("login: get");
        return "/login.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        context.log("login: post");

        String login = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = userService.getUserByUserName(login);

        if (!user.isPresent() || !password.equals(user.get().getPassword())){
            return "redirect:/app/login?error";
        }

        context.log(user.toString());

        request.getSession().setAttribute("user",user.get());

        return user.get().getUserType().equals(UserType.USER)
                ?"redirect:/app/user"
                :"redirect:/app/admin";
    }
}
