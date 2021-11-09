package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.dao.CreateTestDatabase;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.UserType;
import ua.alexkras.hotel.service.impl.UserServiceImpl;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserServiceTest {

    static UserServiceImpl userService;
    static User testUser3 = User.builder()
            .id(3L)
            .name("Admin").surname("Adminovich")
            .username("someUsername").password("password1")
            .phoneNumber("+404-23-4567890")
            .birthday(LocalDate.parse("2002-03-07"))
            .gender("Male").userType(UserType.ADMIN).build();

    @BeforeClass
    public static void beforeClass() {
        CreateTestDatabase.createTestDatabase();
        userService = new UserServiceImpl();
    }

    @Before
    public void beforeTest(){
        CreateTestDatabase.createTestDatabase();
    }

    @Test
    public void testFindByUsername1(){
        User testUser = CreateTestDatabase.testUser1;

        User actual = userService.findByUsername(testUser.getUsername())
                .orElseThrow(IllegalStateException::new);

        assertEquals(testUser,actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testFindByUsername2(){
        userService.findByUsername("someUnknownUsername")
                .orElseThrow(IllegalStateException::new);
    }

    @Test
    public void testCreate1(){
        userService.create(testUser3);

        User actual = userService.findByUsername(testUser3.getUsername())
                        .orElseThrow(IllegalStateException::new);

        assertEquals(testUser3,actual);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate2(){
        userService.create(testUser3);
        userService.create(testUser3);
    }


}