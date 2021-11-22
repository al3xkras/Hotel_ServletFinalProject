package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.model.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    public static final String pathBasename = "logout";

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/";
    }
}
