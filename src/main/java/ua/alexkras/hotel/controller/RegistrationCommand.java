package ua.alexkras.hotel.controller;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/registration.jsp";
    }
}
