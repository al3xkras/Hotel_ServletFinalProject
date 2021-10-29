package ua.alexkras.hotel.model;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String executeGet(HttpServletRequest request);
    String executePost(HttpServletRequest request);
}
