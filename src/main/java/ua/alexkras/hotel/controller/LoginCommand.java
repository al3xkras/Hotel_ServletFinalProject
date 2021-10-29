package ua.alexkras.hotel.controller;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    @Override
    public String executeGet(HttpServletRequest request){
        request.getServletContext().log("login: get");

        return "/login.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request){
        request.getServletContext().log("login: post");

        return "/";
    }
}
