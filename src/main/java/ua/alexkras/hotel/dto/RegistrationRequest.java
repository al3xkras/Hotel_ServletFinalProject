package ua.alexkras.hotel.dto;

import ua.alexkras.hotel.model.mysql.MySqlStrings;

import java.text.ParseException;

public class RegistrationRequest {

    private String name;
    private String surname;
    private String username;
    private String password;
    private String passwordConfirm;
    private String birthdayDate;
    private String gender;
    private String phoneNumber;

    public boolean isValid(){
        try {
            MySqlStrings.dateFormat.parse(birthdayDate);
        } catch (ParseException e){
            return false;
        }
        return name!=null && surname!=null && username!=null && password!=null &&
                (password.length()>=8 & password.length()<=25) && passwordConfirm!=null &&
                passwordConfirm.equals(password) && birthdayDate!=null &&
                gender!=null && phoneNumber!=null;
    }

    public String getValidationErrorMessage(){
        try {
            MySqlStrings.dateFormat.parse(birthdayDate);
        } catch (ParseException e){
            return "date.invalid_format";
        }
        return name==null? "name.isempty":
                surname==null? "surname.isempty":
                username==null? "username.isempty":
                password==null? "password.isempty":
                passwordConfirm==null? "passwords.mismatch":
                !passwordConfirm.equals(password)? "passwords.mismatch":
                birthdayDate==null? "birthday.isempty":
                gender==null? "gender.isempty":
                phoneNumber==null? "phone_number.isempty":
                "";
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

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static RegistrationRequestBuilder builder() {
        return new RegistrationRequestBuilder();
    }

    public static final class RegistrationRequestBuilder {
        private String name;
        private String surname;
        private String username;
        private String password;
        private String passwordConfirm;
        private String birthdayDate;
        private String gender;
        private String phoneNumber;

        private RegistrationRequestBuilder() {
        }

        public RegistrationRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RegistrationRequestBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public RegistrationRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        public RegistrationRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegistrationRequestBuilder passwordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
            return this;
        }

        public RegistrationRequestBuilder birthdayDate(String birthdayDate) {
            this.birthdayDate = birthdayDate;
            return this;
        }

        public RegistrationRequestBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public RegistrationRequestBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RegistrationRequest build() {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.passwordConfirm = this.passwordConfirm;
            registrationRequest.birthdayDate = this.birthdayDate;
            registrationRequest.name = this.name;
            registrationRequest.surname = this.surname;
            registrationRequest.username = this.username;
            registrationRequest.password = this.password;
            registrationRequest.gender = this.gender;
            registrationRequest.phoneNumber = this.phoneNumber;
            return registrationRequest;
        }
    }
}
