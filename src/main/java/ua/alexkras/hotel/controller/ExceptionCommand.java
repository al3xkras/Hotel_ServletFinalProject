package ua.alexkras.hotel.controller;

import javax.servlet.http.HttpServletRequest;

public class ExceptionCommand implements GetCommand {
    @Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }
}
