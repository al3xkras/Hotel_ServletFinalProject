package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.dao.CreateTestDatabase;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


public class ReservationServiceTest {

    Reservation testReservation4 = Reservation.builder()
            .id(5L)
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
        reservationService = new ReservationServiceImpl();
    }

    @Before
    public void beforeTest() {
        CreateTestDatabase.createTestDatabase();
    }

    @Test
    public void testCreate1(){
        reservationService.create(testReservation4);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate2(){
        reservationService.create(testReservation4);
        reservationService.create(testReservation4);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate3(){
        reservationService.create(Reservation.builder().build());
    }

    @Test
    public void testFindById1(){
        reservationService.create(testReservation4);

        Reservation testReservation1 = CreateTestDatabase.testReservation1;

        Reservation actual1 = reservationService.findById(testReservation1.getId())
                .orElseThrow(IllegalStateException::new);
        Reservation actual4 = reservationService.findById(testReservation4.getId())
                .orElseThrow(IllegalStateException::new);
        assertEquals(testReservation1, actual1);
        assertEquals(testReservation4,actual4);
    }

    @Test(expected = IllegalStateException.class)
    public void testFindById2(){
        Reservation unknown = reservationService.findById(-1000)
                .orElseThrow(IllegalStateException::new);
    }

    @Test
    public void testGetReservationsCountByActiveAndStatus(){
        assertEquals(2,reservationService.getReservationsCountByActiveAndStatus(true,ReservationStatus.PENDING));
        reservationService.create(testReservation4);
        assertEquals(3,reservationService.getReservationsCountByActiveAndStatus(true,ReservationStatus.PENDING));
        assertEquals(0,reservationService.getReservationsCountByActiveAndStatus(false,ReservationStatus.PENDING));
        assertEquals(1,reservationService.getReservationsCountByActiveAndStatus(false,ReservationStatus.CANCELLED));
    }

    @Test
    public void testFindAllByActiveAndStatus(){
        Pageable pageable1 = new Pageable(10, 100);
        Pageable pageable2 = new Pageable(1, 100);

        List<Reservation> actual1 = reservationService.findAllByActiveAndStatus(true,ReservationStatus.PENDING, pageable1);
        List<Reservation> actual2 = reservationService.findAllByActiveAndStatus(true,ReservationStatus.PENDING, pageable2);
        List<Reservation> actual3 = reservationService.findAllByActiveAndStatus(false,ReservationStatus.CANCELLED, pageable2);
        List<Reservation> actual4 = reservationService.findAllByActiveAndStatus(false,ReservationStatus.PENDING, pageable2);

        assertEquals(2,actual1.size());
        assertEquals(1,actual2.size());
        assertEquals(1,actual3.size());
        assertEquals(actual3.get(0),CreateTestDatabase.testReservation4);
        assertEquals(0,actual4.size());
    }

    @Test
    public void testGetReservationsCountByUserIdAndActiveAndAnyStatusExcept(){
        int actual1 = reservationService.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
                1L,true,ReservationStatus.CANCELLED);
        int actual2 = reservationService.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
                1L,true,ReservationStatus.PENDING);
        int actual3 = reservationService.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
                2L,true,ReservationStatus.EXPIRED);
        int actual4 = reservationService.getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
                1L,false,ReservationStatus.PENDING);

        assertEquals(2,actual1);
        assertEquals(0,actual2);
        assertEquals(1,actual3);
        assertEquals(1,actual4);
    }

    @Test
    public void testFindByUserIdAndActiveAndAnyStatusExcept(){
        Pageable pageable1 = new Pageable(10, 100);
        Pageable pageable2 = new Pageable(1, 100);

        List<Reservation> actual1 = reservationService.findByUserIdAndActiveAndAnyStatusExcept(
                1L,true,ReservationStatus.CANCELLED,pageable1);
        List<Reservation> actual2 = reservationService.findByUserIdAndActiveAndAnyStatusExcept(
                1L,true,ReservationStatus.CANCELLED,pageable2);
        List<Reservation> actual3 = reservationService.findByUserIdAndActiveAndAnyStatusExcept(
                2L,true,ReservationStatus.EXPIRED,pageable2);
        List<Reservation> actual4 = reservationService.findByUserIdAndActiveAndAnyStatusExcept(
                3L,false,ReservationStatus.PENDING,pageable2);

        assertTrue(actual1.contains(CreateTestDatabase.testReservation2));
        assertEquals(2,actual1.size());
        assertEquals(1,actual2.size());

        assertEquals(1,actual3.size());
        assertEquals(actual3.get(0),CreateTestDatabase.testReservation3);

        assertEquals(actual4.size(),0);
    }

    @Test
    public void testGetReservationFullCost(){
        Reservation test1 = CreateTestDatabase.testReservation1;
        Reservation test2 = CreateTestDatabase.testReservation2;

        assertEquals(test1.getApartmentPrice().longValue(),reservationService.getReservationFullCost(test1));
        assertEquals(test2.getApartmentPrice().longValue()*5L,reservationService.getReservationFullCost(test2));
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