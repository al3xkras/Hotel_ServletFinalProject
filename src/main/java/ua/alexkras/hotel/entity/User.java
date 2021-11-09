package ua.alexkras.hotel.entity;

import ua.alexkras.hotel.dto.RegistrationRequest;
import ua.alexkras.hotel.model.UserType;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.dateFormat;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private LocalDate birthday;
    private String gender;
    private String phoneNumber;
    private UserType userType;

    public User(RegistrationRequest dto, UserType userType){

        this.name=dto.getName();
        this.surname=dto.getSurname();
        this.username=dto.getUsername();
        this.password=dto.getPassword();
        try {
            this.birthday = dateFormat
                    .parse(dto.getBirthdayDate())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.gender=dto.getGender();
        this.phoneNumber=dto.getPhoneNumber();
        this.userType=userType;
    }

    public User() {

    }

    public Long getId() {
        return id;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && name.equals(user.name) && surname.equals(user.surname) && username.equals(user.username) && password.equals(user.password) && birthday.equals(user.birthday) && gender.equals(user.gender) && phoneNumber.equals(user.phoneNumber) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, username, password, birthday, gender, phoneNumber, userType);
    }

    @Override
    public String toString() {
        return "User: [" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType=" + userType +
                ']';
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public static final class UserBuilder {

        private long id;
        private String name;
        private String surname;
        private String username;
        private String password;
        private LocalDate birthday;
        private String gender;
        private String phoneNumber;
        private UserType userType;

        private UserBuilder() {

        }

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder userType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public User build() {
            User user = new User();
            user.surname = this.surname;
            user.password = this.password;
            user.id = this.id;
            user.gender = this.gender;
            user.name = this.name;
            user.birthday = this.birthday;
            user.phoneNumber = this.phoneNumber;
            user.username = this.username;
            user.userType = this.userType;
            return user;
        }
    }
}
