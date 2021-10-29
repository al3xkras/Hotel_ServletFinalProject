package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.dto.RegistrationRequest;
import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements GetCommand,PostCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return (this instanceof GetCommand) ? executeGet(request) : executePost(request);
    }

    public String executeGet(HttpServletRequest request){
        request.setAttribute("registrationRequest",new RegistrationRequest());
        request.setAttribute("usernameExists",false);
        request.setAttribute("passwordMismatch",false);

        return "/WEB-INF/registration.jsp";
    }

    public String executePost(HttpServletRequest request) {
        return "/WEB-INF/index.jsp";
    }
}
