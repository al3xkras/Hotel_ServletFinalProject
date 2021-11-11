package ua.alexkras.hotel.commands.admin_or_user;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;

import javax.servlet.http.HttpServletRequest;

public class ProfileCommand implements Command {

    public static final String pathBasename = "profile";

    @Override
    public String executeGet(HttpServletRequest request) {

        User user = AuthFilter.getCurrentLoginUser()
                .orElseThrow(IllegalStateException::new);

        request.setAttribute("user",user);
        return "/WEB-INF/personal_area/profile.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return executeGet(request);
    }
}
