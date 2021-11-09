package ua.alexkras.hotel.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.UserType;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserServiceITest {

    static User testUser3 = User.builder()
            .id(-1L)
            .name("Admin").surname("Adminovich")
            .username("Admin1").password("password1")
            .phoneNumber("+404-23-4567890")
            .birthday(LocalDate.parse("2002-03-07"))
            .gender("Male").userType(UserType.ADMIN).build();

    @BeforeClass
    public static void beforeClass(){

    }

    @Test
    public void testFindById(){

    }

    @Test
    public void testFindByUsername(){

    }

    @Test
    public void testCreate(){

    }
}