package ua.alexkras.hotel.filter;

import ua.alexkras.hotel.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static Optional<User> currentLoginUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();

        Optional<User> loggedUser = Optional.ofNullable((User)session.getAttribute("user"));
        currentLoginUser = loggedUser;

        loggedUser.ifPresent(user -> context.log(user.toString()));

        filterChain.doFilter(request,response);
    }

    public static Optional<User> getCurrentLoginUser(){
        return currentLoginUser;
    }

    public static void clearCurrentLoginUser(){
        currentLoginUser = Optional.empty();
    }

    @Override
    public void destroy() {

    }
}
