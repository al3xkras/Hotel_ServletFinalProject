package ua.alexkras.hotel.dto;

import java.time.LocalDate;

public class RegistrationRequest {
    //@NotEmpty
    private String name;

    //@NotEmpty
    private String surname;

    //@NotEmpty
    private String username;

    //@NotEmpty
    //@Size(min=8, max = 25)
    private String password;

    //@NotEmpty
    //@Size(min=8, max = 25)
    private String passwordConfirm;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    //@NotEmpty
    private String gender;

    //@NotEmpty
    private String phoneNumber;

    public boolean isValid(){
        return false;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
