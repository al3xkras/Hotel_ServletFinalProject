package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.dto.RegistrationRequest;
import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    @Override
    public String executeGet(HttpServletRequest request){
        request.getServletContext().log("registration: get");

        request.setAttribute("registrationRequest",new RegistrationRequest());
        request.setAttribute("usernameExists",false);
        request.setAttribute("passwordMismatch",false);

        return "/WEB-INF/registration.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        request.getServletContext().log("registration: get");

        //request.getAttribute("usernameExists");
        return "/";
    }
}
