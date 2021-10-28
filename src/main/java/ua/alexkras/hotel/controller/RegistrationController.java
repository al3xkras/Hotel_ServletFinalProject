package ua.alexkras.hotel.controller;


import ua.alexkras.hotel.dto.RegistrationRequest;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.UserService;
import javax.validation.Valid;

public class RegistrationController {

    private final UserService userService;
    public RegistrationController(UserService userService){
        this.userService=userService;
    }

    /*
    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("registrationRequest", new RegistrationRequest());
        model.addAttribute("usernameExists",false);
        model.addAttribute("passwordMismatch",false);
        return "registration";
    }

    @PostMapping("/registration")
    public String sendRegistrationRequest(
            @Valid @ModelAttribute("registrationRequest") RegistrationRequest request,
            BindingResult result,
            Model model){
        if (result.hasErrors()){
            return "registration";
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())){
            model.addAttribute("passwordMismatch",true);
            return "registration";
        }

        User user = new User(request, UserType.USER);

        try{
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("usernameExists",true);
            return "registration";
        }

        return "index";
    }

     */

}
