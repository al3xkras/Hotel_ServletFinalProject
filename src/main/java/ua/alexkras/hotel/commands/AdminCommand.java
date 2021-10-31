package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AdminCommand implements Command {
    @Override
    public String executeGet(HttpServletRequest request) {
        Optional<User> currentUser = AuthFilter.getCurrentLoginUser();

        if(!currentUser
                .orElseThrow(IllegalStateException::new)
                .getUserType().equals(UserType.ADMIN)){
            return "redirect:/";
        }

        return "/WEB-INF/personal_area/admin.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "/";
    }
}
