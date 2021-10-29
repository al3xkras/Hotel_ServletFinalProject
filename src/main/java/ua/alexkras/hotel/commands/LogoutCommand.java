package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.filter.AuthFilter;
import ua.alexkras.hotel.model.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String executeGet(HttpServletRequest request) {
        AuthFilter.clearCurrentLoginUser();
        request.getSession().setAttribute("user",null);
        return "";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        AuthFilter.clearCurrentLoginUser();
        return "/";
    }
}
