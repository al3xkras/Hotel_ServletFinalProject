package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.FirstLaunch;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.model.mysql.MySqlStrings;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;
import ua.alexkras.hotel.service.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.databaseName;

public class CreateTestDatabase {
    public static User testUser1 = User.builder()
            .id(1L)
            .name("UserName1").surname("UserSurname1")
            .username("zzz").password("q")
            .phoneNumber("+404-12-3456789")
            .birthday(LocalDate.parse("2002-03-07"))
            .gender("Male").userType(UserType.USER).build();

    public static User testUser2 = User.builder()
            .id(2L)
            .name("UserName2").surname("UserSurname2")
            .username("zzz zzz").password("q")
            .phoneNumber("+404-12-6545656")
            .birthday(LocalDate.parse("2005-08-12"))
            .gender("Male").userType(UserType.USER).build();

    public static User testUser3 = User.builder()
            .id(-1L)
            .name("Admin").surname("Adminovich")
            .username("Admin1").password("password1")
            .phoneNumber("+404-23-4567890")
            .birthday(LocalDate.parse("2002-03-07"))
            .gender("Male").userType(UserType.ADMIN).build();

    public static Apartment testApartment1 = Apartment.builder()
            .id(1L)
            .name("test apartment 1")
            .places(3)
            .apartmentClass(ApartmentClass.ClassA)
            .status(ApartmentStatus.AVAILABLE)
            .price(1000)
            .build();

    public static Apartment testApartment2 = Apartment.builder()
            .id(2L)
            .name("test apartment 2")
            .places(2)
            .apartmentClass(ApartmentClass.ClassC)
            .status(ApartmentStatus.AVAILABLE)
            .price(2000)
            .build();

    public static Apartment testApartment3 = Apartment.builder()
            .id(3L)
            .name("test apartment 3")
            .places(2)
            .apartmentClass(ApartmentClass.ClassC)
            .status(ApartmentStatus.UNAVAILABLE)
            .price(234343)
            .build();

    public static Reservation matchReservation1 = Reservation.builder()
            .id(-1L)
            .places(2)
            .apartmentClass(ApartmentClass.ClassC)
            .build();

    public static Reservation matchReservation2 = Reservation.builder()
            .id(-1L)
            .places(4)
            .apartmentClass(ApartmentClass.ClassA)
            .build();

    public static Reservation testReservation1 = Reservation.builder()
            .id(1L)
            .userId(1L)
            .apartmentId(2L)
            .apartmentClass(ApartmentClass.ClassA)
            .places(2)
            .apartmentPrice(1000)
            .reservationStatus(ReservationStatus.PENDING)
            .fromDate(LocalDate.parse("2021-10-10"))
            .toDate(LocalDate.parse("2021-10-11"))
            .submitDate(LocalDate.parse("2021-09-08").atStartOfDay())
            .adminConfirmationDate(null)
            .isPaid(false)
            .isActive(true)
            .expired(false)
            .build();

    public static Reservation testReservation2 = Reservation.builder()
            .id(2L)
            .userId(1L)
            .apartmentId(3L)
            .apartmentClass(ApartmentClass.ClassC)
            .places(3)
            .apartmentPrice(1000)
            .reservationStatus(ReservationStatus.PENDING)
            .fromDate(LocalDate.parse("2021-10-10"))
            .toDate(LocalDate.parse("2021-10-15"))
            .submitDate(LocalDate.parse("2021-09-08").atStartOfDay())
            .adminConfirmationDate(null)
            .isPaid(false)
            .isActive(true)
            .expired(false)
            .build();

    public static Reservation testReservation3 = Reservation.builder()
            .id(3L)
            .userId(1L)
            .apartmentId(3L)
            .apartmentClass(ApartmentClass.ClassD)
            .places(3)
            .apartmentPrice(1000)
            .reservationStatus(ReservationStatus.CANCELLED)
            .fromDate(LocalDate.parse("2021-10-10"))
            .toDate(LocalDate.parse("2021-10-20"))
            .submitDate(LocalDate.parse("2021-09-08").atStartOfDay())
            .adminConfirmationDate(null)
            .isPaid(false)
            .isActive(true)
            .expired(false)
            .build();

    public static void deleteTestDatabase(){
        if (!databaseName.toLowerCase().endsWith("test")){
            throw new IllegalStateException("Attempting to Truncate tables of NON-TEST DATABASE");
        }

        try(Connection conn = DriverManager.getConnection(MySqlStrings.root, MySqlStrings.user, MySqlStrings.password);
            PreparedStatement deleteTestDatabase = conn.prepareStatement("DROP SCHEMA "+databaseName)
        ){
            deleteTestDatabase.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static void createTestDatabase(){
        try {
            deleteTestDatabase();
        } catch (RuntimeException ignored){

        }
        FirstLaunch.createDatabase();

        UserServiceImpl userService = new UserServiceImpl();
        ReservationServiceImpl reservationService = new ReservationServiceImpl();
        ApartmentServiceImpl apartmentService = new ApartmentServiceImpl();

        userService.create(testUser1);
        userService.create(testUser2);
        userService.create(testUser3);
        apartmentService.create(testApartment1);
        apartmentService.create(testApartment2);
        apartmentService.create(testApartment3);

        reservationService.create(testReservation1);
        reservationService.create(testReservation2);
        reservationService.create(testReservation3);
    }
}
