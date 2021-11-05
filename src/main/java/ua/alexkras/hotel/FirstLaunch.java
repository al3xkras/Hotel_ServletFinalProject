package ua.alexkras.hotel;

import ua.alexkras.hotel.dao.UserDAO;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.mysql.*;
import ua.alexkras.hotel.model.UserType;
import java.sql.*;
import java.time.LocalDate;


public class FirstLaunch {
    public static void main(String[] args) {
        //Create database if not exists before starting application
        try (Connection conn = DriverManager.getConnection(MySqlStrings.root, MySqlStrings.user, MySqlStrings.password);
             PreparedStatement createDB = conn.prepareStatement(MySqlStrings.sqlCreateDatabaseIfNotExists);
             PreparedStatement createUserTable = conn.prepareStatement(UserTableStrings.sqlCreateUserTableIfNotExists);
             PreparedStatement createApartmentTable = conn.prepareStatement(ApartmentTableStrings.sqlCreateApartmentTableIfNotExists);
             PreparedStatement createReservationTable = conn.prepareStatement(ReservationTableStrings.sqlCreateReservationTableIfNotExists);
             PreparedStatement createPaymentsTable = conn.prepareStatement(PaymentTableStrings.createPaymentsTableIfNotExists)
            ){
            createDB.execute();
            createUserTable.execute();
            createApartmentTable.execute();
            createReservationTable.execute();
            createPaymentsTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create database: "+ MySqlStrings.databaseName);
        }

        UserDAO userDAO = JDBCDaoFactory.getInstance().createUserDAO();

        userDAO.create(User.builder()
                .id(-1L)
                .name("Admin").surname("Adminovich")
                .username("Admin1").password("password1")
                .phoneNumber("+404-23-4567890")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.ADMIN).build());

        userDAO.create(User.builder()
                .id(-2L)
                .name("AdminName").surname("AdminSurname")
                .username("Admin2").password("password2")
                .phoneNumber("+404-12-3456789")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.ADMIN).build());

        userDAO.create(User.builder()
                .id(-3L)
                .name("AdminName").surname("AdminSurname")
                .username("zzz").password("q")
                .phoneNumber("+404-12-3456789")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.USER).build());
    }



}
