package ua.alexkras.hotel.controller;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements GetCommand, PostCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return (this instanceof GetCommand) ? executeGet(request) : executePost(request);
    }

    private String executeGet(HttpServletRequest request){
        return "/login.jsp";
    }

    private String executePost(HttpServletRequest request){
        return "/WEB-INF/index.jsp";
    }
}
