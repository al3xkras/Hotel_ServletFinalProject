package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.model.Command;

import javax.servlet.http.HttpServletRequest;

public class ExceptionCommand implements Command {
    public static final String pathBasename = "error";

    @Override
    public String executeGet(HttpServletRequest request) {
        return "redirect:/";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return "redirect:/";
    }
}
