package ua.alexkras.hotel.filter;

import ua.alexkras.hotel.HotelServlet;
import ua.alexkras.hotel.commands.LoginCommand;
import ua.alexkras.hotel.commands.LogoutCommand;
import ua.alexkras.hotel.commands.RegistrationCommand;
import ua.alexkras.hotel.commands.admin.AdminCommand;
import ua.alexkras.hotel.commands.admin_or_user.ApartmentCommand;
import ua.alexkras.hotel.commands.admin_or_user.ProfileCommand;
import ua.alexkras.hotel.commands.user.UserCommand;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.AccessDeniedException;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static User currentLoginUser;

    private final Set<String> anonymousCommands = new HashSet<>();

    private String previousPageUrl = "";

    @Override
    public void init(FilterConfig filterConfig) {
        anonymousCommands.add(LoginCommand.pathBasename);
        anonymousCommands.add(RegistrationCommand.pathBasename);

        //adminOrUserCommands.add(ApartmentCommand.pathBasename);
        //adminOrUserCommands.add(ProfileCommand.pathBasename);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        currentLoginUser = (User)session.getAttribute("user");

        String url = req.getRequestURI();
        if (previousPageUrl.equals(url)){
            filterChain.doFilter(request,response);
            return;
        }
        previousPageUrl = url;

        String command = Command.getCommand(url,HotelServlet.pathBasename);

        if (currentLoginUser!=null) {
            if (command.equals(LogoutCommand.pathBasename)){
                currentLoginUser=null;
                session.setAttribute("user",null);
            } else if (!command.isEmpty()){
                request.getServletContext().log(command);
                filterByUserType(currentLoginUser.getUserType(), command);
            } else {
                res.sendRedirect(req.getContextPath()+'/'+
                        HotelServlet.pathBasename+'/'+
                        currentLoginUser.getUserType().name().toLowerCase(Locale.ROOT));
            }
            //req.getServletContext().log("filtered by user type");
        } else {
            //request.getServletContext().log(url);
            if (    !command.isEmpty() &
                    !anonymousCommands.contains(command)){
                //request.getServletContext().log("Attempting to access command: \""+command+"\" from an anonymous user");
                throw new AccessDeniedException();
            }
        }

        filterChain.doFilter(request,response);
    }

    private void filterByUserType(UserType userType, String command) throws AccessDeniedException{
        if ((command.startsWith(UserCommand.pathBasename) && !userType.equals(UserType.USER)) ||
                (command.startsWith(AdminCommand.pathBasename) && !userType.equals(UserType.ADMIN))){
            throw new AccessDeniedException();
        }
    }

    public static Optional<User> getCurrentLoginUser(){
        return Optional.ofNullable(currentLoginUser);
    }

    @Override
    public void destroy() {
    }
}
