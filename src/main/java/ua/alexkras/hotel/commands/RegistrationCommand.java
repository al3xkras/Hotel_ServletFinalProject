package ua.alexkras.hotel.commands;

import ua.alexkras.hotel.dto.RegistrationRequest;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.Command;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand implements Command {
    public static final String pathBasename = "registration";

    private final UserService<Pageable> userService;
    public final Map<String,String> validationErrorMap;

    public RegistrationCommand(UserService<Pageable> userService){
        this.userService=userService;
        Map<String,String> map = new HashMap<>();
        map.put("name.isempty","name");
        map.put("surname.isempty","surname");
        map.put("username.isempty","username");
        map.put("username.exists","username");
        map.put("password.isempty","password");
        map.put("passwords.mismatch","password");
        map.put("birthday.isempty","birthday");
        map.put("date.invalid_format","birthday");
        map.put("gender.isempty","gender");
        map.put("phone_number.isempty","phone_number");

        validationErrorMap=Collections.unmodifiableMap(map);
    }

    @Override
    public String executeGet(HttpServletRequest request){
        request.setAttribute("clear_session_storage",true);
        return "/WEB-INF/registration.jsp";
    }

    @Override
    public String executePost(HttpServletRequest request) {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .name(request.getParameter("name"))
                .surname(request.getParameter("surname"))
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .passwordConfirm(request.getParameter("passwordConfirm"))
                .birthdayDate(request.getParameter("birthdayDate"))
                .gender(request.getParameter("gender"))
                .phoneNumber(request.getParameter("phoneNumber"))
                .build();

        String validationErrorMessage = registrationRequest.getValidationErrorMessage();

        User user = new User();

        if (validationErrorMessage.isEmpty()){
            user = new User(registrationRequest, UserType.USER);
            try{
                userService.create(user);
            } catch (Exception e){
                validationErrorMessage="username.exists";
            }
        }

        if (!validationErrorMessage.isEmpty()){
            request.setAttribute("errorField",validationErrorMap.get(validationErrorMessage));
            request.setAttribute("errorMessage",validationErrorMessage);
            request.getServletContext().log(validationErrorMessage);
            return "/WEB-INF/registration.jsp";
        }

        request.getServletContext().log("Successfully registered: "+user);

        return "redirect:/app/login";
    }
}
