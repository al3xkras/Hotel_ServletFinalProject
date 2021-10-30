package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.Command;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

        Optional<User> user = JDBCDaoFactory.getInstance().createUserDAO().findByUsername(login);

        if (!user.isPresent() || !password.equals(user.get().getPassword())){
            return "/app/login?error";
        }

        context.log(user.toString());

        request.getSession().setAttribute("user",user.get());

        return "/app/personal_area";
    }
}
