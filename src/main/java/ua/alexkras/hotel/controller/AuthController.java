package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.model.HotelUserDetailsService;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.service.UserService;
import java.util.Optional;

//@RequestMapping("/auth")
public class AuthController {

    private static User currentUser;
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

    //@GetMapping("/login")
    public String getLoginPage(){
        return "/auth/login";
    }

    public Optional<User> getCurrentUser(){
        return null;
    }

    private void updateCurrentUser(String username) {
        if (currentUser!=null && currentUser.getUsername().equalsIgnoreCase(username)){
            return;
        }
        currentUser = userService.getUserByUserName(username).orElseThrow(IllegalStateException::new);
    }

    public void clearCurrentUser(){
        currentUser=null;
    }

}
