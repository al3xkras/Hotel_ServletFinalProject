package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.model.Command;

import javax.servlet.http.HttpServletRequest;

public class ExceptionCommand implements Command {
    @Override
    public String executeGet(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }

    @Override
    public String executePost(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }
}