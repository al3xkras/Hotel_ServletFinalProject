package ua.alexkras.hotel;

import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.MySqlStrings;
import ua.alexkras.hotel.model.UserType;
import java.sql.*;
import java.time.LocalDate;


public class FirstLaunch {
    public static void main(String[] args) {
        //Create database if not exists before starting Spring Boot application
        try (Connection conn = DriverManager.getConnection(MySqlStrings.root, MySqlStrings.user, MySqlStrings.password);
             PreparedStatement createDB = conn.prepareStatement(MySqlStrings.sqlCreateDatabaseIfNotExists);
        ) {
            createDB.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create database: "+MySqlStrings.databaseName);
        }

        //SpringApplication.run(HotelApplication.class, args);

        addUser(-1L,new User().builder()
                .name("Admin").surname("Adminovich")
                .username("Admin1").password("password1")
                .phoneNumber("+404-23-4567890")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.ADMIN).build());

        addUser(-2L,new User().builder()
                .name("AdminName").surname("AdminSurname")
                .username("Admin2").password("password2")
                .phoneNumber("+404-12-3456789")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.ADMIN).build());

    }


    public static void addUser(long id, User user) {
        //PasswordEncoder encoder = HotelUserDetailsService.passwordEncoder();

        try(Connection conn = DriverManager.getConnection(MySqlStrings.connectionUrl, MySqlStrings.user, MySqlStrings.password);
            PreparedStatement addUserIfNotExists = conn.prepareStatement("INSERT INTO hotel_db.user " +
                    "(id, birthday, gender, name, password, phone_number, surname, user_type, username)" +
                    " VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ){

            addUserIfNotExists.setLong(1,id);
            addUserIfNotExists.setDate(2, Date.valueOf(user.getBirthday()));
            addUserIfNotExists.setString(3, user.getGender());
            addUserIfNotExists.setString(4, user.getName());
            //addUserIfNotExists.setString(5, encoder.encode(user.getPassword()));
            addUserIfNotExists.setString(6, user.getPhoneNumber());
            addUserIfNotExists.setString(7, user.getSurname());
            addUserIfNotExists.setString(8, user.getUserType().name());
            addUserIfNotExists.setString(9, user.getUsername());

            addUserIfNotExists.execute();

        } catch (SQLIntegrityConstraintViolationException ignored){

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
