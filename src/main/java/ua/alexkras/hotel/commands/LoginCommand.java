package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.dao.UserDAO;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.UsernameNotFoundException;
import ua.alexkras.hotel.model.Command;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

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

        User user = UserDAO.getUserByUsername(login).orElseThrow(()->new UsernameNotFoundException(login));

        if (!password.equals(user.getPassword())){
            return "/";
        }

        context.log(user.toString());
        context.setAttribute("user",user);

        return "/personal_area";
    }
}
