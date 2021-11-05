package ua.alexkras.hotel.filter;

import ua.alexkras.hotel.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static User currentLoginUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();

        currentLoginUser = (User)session.getAttribute("user");

        filterChain.doFilter(request,response);
    }

    public static Optional<User> getCurrentLoginUser(){
        return Optional.ofNullable(currentLoginUser);
    }

    public static void clearCurrentLoginUser(){
        currentLoginUser = null;
    }

    @Override
    public void destroy() {

    }
}
