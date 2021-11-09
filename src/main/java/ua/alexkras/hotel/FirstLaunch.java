package ua.alexkras.hotel;

import ua.alexkras.hotel.dao.ApartmentDao;
import ua.alexkras.hotel.dao.UserDAO;
import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.mysql.*;
import ua.alexkras.hotel.model.UserType;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.databaseName;
import static ua.alexkras.hotel.model.mysql.PaymentTableStrings.*;
import static ua.alexkras.hotel.model.mysql.PaymentTableStrings.colReservationId;
import static ua.alexkras.hotel.model.mysql.PaymentTableStrings.colUserId;
import static ua.alexkras.hotel.model.mysql.ReservationTableStrings.*;
import static ua.alexkras.hotel.model.mysql.UserTableStrings.tableUser;

public class FirstLaunch {
    public static void main(String[] args) {
        createDatabase();

        ApartmentDao apartmentDao = JDBCDaoFactory.getInstance().createApartmentDAO();
        apartmentDao.create(Apartment.builder()
                        .name("-")
                        .places(0)
                        .apartmentClass(ApartmentClass.ClassA)
                        .status(ApartmentStatus.OCCUPIED)
                        .price(0)
                        .build());

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
                .name("UserName").surname("UserSurname")
                .username("zzz").password("q")
                .phoneNumber("+404-12-3456789")
                .birthday(LocalDate.parse("2002-03-07"))
                .gender("Male").userType(UserType.USER).build());



    }

    public static void createDatabase(){
        try (Connection conn = DriverManager.getConnection(MySqlStrings.root, MySqlStrings.user, MySqlStrings.password);
             PreparedStatement createDB = conn.prepareStatement(MySqlStrings.sqlCreateDatabaseIfNotExists);
             PreparedStatement createUserTable = conn.prepareStatement(UserTableStrings.sqlCreateUserTableIfNotExists);
             PreparedStatement createApartmentTable = conn.prepareStatement(ApartmentTableStrings.sqlCreateApartmentTableIfNotExists);
             PreparedStatement createReservationTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "+
                     databaseName+"."+tableReservation+" ("+
                     ReservationTableStrings.colReservationId+" int unique primary key auto_increment, "+
                     ReservationTableStrings.colUserId+" int not null, FOREIGN KEY ("+ReservationTableStrings.colUserId+")"+
                     " REFERENCES "+databaseName+"."+tableUser+"("+UserTableStrings.colUserId+")" +
                     " ON DELETE CASCADE,"+
                     colApartmentId+" int, FOREIGN KEY (apartment_id)" +
                     "REFERENCES "+databaseName+"."+ApartmentTableStrings.tableApartment+"("+ApartmentTableStrings.colApartmentId+") ON DELETE NO ACTION, "+
                     colApartmentClass+" varchar(20) not null,"+
                     colApartmentPlaces+" int not null,"+
                     colApartmentPrice+" int,"+
                     colReservationStatus+" varchar(20) not null,"+
                     colFromDate+" DATE not null,"+
                     colToDate+" DATE not null,"+
                     colSubmitDate+" DATETIME not null,"+
                     colAdminConfirmationDate+" DATE,"+
                     colIsPaid+" boolean default 0,"+
                     colIsActive+" boolean default 1,"+
                     colIsExpired+" boolean default 0) ENGINE=INNODB;");
             PreparedStatement createPaymentsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "+
                     databaseName+"."+tablePayments+" ("+
                     colPaymentId+" int unique primary key auto_increment, "+
                     colUserId+" int not null," +
                     "FOREIGN KEY (user_id) REFERENCES "+databaseName+"."+tableUser+"("+UserTableStrings.colUserId+") ON DELETE NO ACTION,"+
                     colReservationId+" int not null," +
                     "FOREIGN KEY (reservation_id) REFERENCES  "+databaseName+"."+tableReservation+"("+ReservationTableStrings.colReservationId+") ON DELETE NO ACTION,"+
                     colValue+" int not null,"+
                     colPaymentDate+" DATETIME not null,"+
                     colCardNumber+" varchar(40) not null,"+
                     colCardExpirationDate+" DATE not null,"+
                     colCardCvv+" varchar(3) not null) ENGINE=INNODB;")
        ){
            createDB.execute();
            createUserTable.execute();
            createApartmentTable.execute();
            createReservationTable.execute();
            createPaymentsTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create database: "+ databaseName);
        }
    }



}
