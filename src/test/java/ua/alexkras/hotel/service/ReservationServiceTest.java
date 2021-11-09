package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.dao.CreateTestDatabase;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import java.time.LocalDate;


public class ReservationServiceTest {

    Reservation testReservation4 = Reservation.builder()
            .id(4L)
            .userId(2L)
            .apartmentId(3L)
            .apartmentClass(ApartmentClass.ClassC)
            .places(3)
            .apartmentPrice(1000)
            .reservationStatus(ReservationStatus.PENDING)
            .fromDate(LocalDate.parse("2020-09-07"))
            .toDate(LocalDate.parse("2020-09-05"))
            .submitDate(LocalDate.parse("2008-01-11").atStartOfDay())
            .adminConfirmationDate(null)
            .isPaid(false)
            .isActive(true)
            .expired(false)
            .build();

    static ReservationServiceImpl reservationService;

    @BeforeClass
    public static void beforeClass(){
        CreateTestDatabase.createTestDatabase();
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testCreate1(){
        reservationService.create(testReservation4);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate2(){
        reservationService.create(testReservation4);
    }

    @Test
    public void testFindById(){


    }

    @Test
    public void testFindByReservationStatus(){


    }

    @Test
    public void testFindByUserIdAndActiveAndAnyStatusExcept(){


    }

    @Test
    public void testGetReservationFullCost(){


    }

    @Test
    public void testGetReservationsCountByUserIdAndActiveAndAnyStatusExcept(){


    }

    @Test
    public void testGetReservationsCountByActiveAndStatus(){


    }

    @Test
    public void testUpdateStatusById(){


    }

    @Test
    public void testUpdateStatusAndConfirmationDateById(){


    }

    @Test
    public void testUpdateIsPaidById(){


    }

    @Test
    public void testUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(){


    }

    @Test
    public void testUpdateAllExpiredReservations(){


    }
}