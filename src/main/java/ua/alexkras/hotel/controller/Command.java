package ua.alexkras.hotel.controller;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String executeGet(HttpServletRequest request);
    String executePost(HttpServletRequest request);
}
