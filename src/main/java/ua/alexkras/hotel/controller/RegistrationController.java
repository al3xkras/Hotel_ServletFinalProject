package ua.alexkras.hotel.controller;

import ua.alexkras.hotel.service.UserService;

public class RegistrationController {

    private final UserService userService;
    public RegistrationController(UserService userService){
        this.userService=userService;
    }

    /*

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
