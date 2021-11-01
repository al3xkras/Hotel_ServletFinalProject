package ua.alexkras.hotel.model;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String executeGet(HttpServletRequest request);
    String executePost(HttpServletRequest request);

    static String getCommand(String uri, String commandBasename){
        String command = uri.replaceAll(".*/"+ commandBasename, "");
        if (command.isEmpty())
            return command;
        int i;
        command = (command.charAt(0)=='/')?command.substring(1):command;
        command = (i = command.indexOf('/'))<0?command:command.substring(0,i);
        return command;
    }
}
