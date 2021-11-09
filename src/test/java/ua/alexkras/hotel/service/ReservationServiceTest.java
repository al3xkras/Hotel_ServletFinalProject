package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.test_db_connection.TestDatabase;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


public class ReservationServiceTest {

    Reservation testReservation = Reservation.builder()
            .id(100L)
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
        TestDatabase.createTestDatabase();
        reservationService = new ReservationServiceImpl();
    }

    @Before
    public void beforeTest() {
        TestDatabase.createTestDatabase();
    }

    @Test
    public void testCreate1(){
        reservationService.create(testReservation);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate2(){
        reservationService.create(testReservation);
        reservationService.create(testReservation);
    }

    @Test(expected = RuntimeException.class)
    public void testCreate3(){
        reservationService.create(Reservation.builder().build());
    }

    @Test
    public void testFindById1(){
        reservationService.create(testReservation);

        Reservation testReservation1 = TestDatabase.testReservation1;

        Reservation actual1 = reservationService.findById(testReservation1.getId())
                .orElseThrow(IllegalStateException::new);
        Reservation actual = reservationService.findById(testReservation.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(testReservation1, actual1);
        assertEquals(testReservation,actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testFindById2(){
        reservationService.findById(-1000)
                .orElseThrow(IllegalStateException::new);
    }

    @Test
    public void testGetReservationsCountByActiveAndStatus(){
        assertEquals(2,reservationService.getReservationsCountByActiveAndStatus(true,ReservationStatus.PENDING));
        reservationService.create(testReservation);
        assertEquals(3,reservationService.getReservationsCountByActiveAndStatus(true,ReservationStatus.PENDING));
        assertEquals(1,reservationService.getReservationsCountByActiveAndStatus(false,ReservationStatus.PENDING));
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
        assertEquals(actual3.get(0), TestDatabase.testReservation4);
        assertEquals(1,actual4.size());
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

        assertEquals(3,actual1);
        assertEquals(1,actual2);
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

        assertTrue(actual1.contains(TestDatabase.testReservation2));
        assertEquals(3,actual1.size());
        assertEquals(1,actual2.size());

        assertEquals(1,actual3.size());
        assertEquals(actual3.get(0), TestDatabase.testReservation3);

        assertEquals(actual4.size(),0);
    }

    @Test
    public void testGetReservationFullCost(){
        Reservation test1 = TestDatabase.testReservation1;
        Reservation test2 = TestDatabase.testReservation2;

        assertEquals(test1.getApartmentPrice().longValue(),reservationService.getReservationFullCost(test1));
        assertEquals(test2.getApartmentPrice().longValue()*5L,reservationService.getReservationFullCost(test2));
    }

    @Test
    public void testUpdateStatusById(){
        Reservation beforeUpdate = testReservation;

        reservationService.create(beforeUpdate);
        reservationService.updateStatusById(beforeUpdate.getId(),ReservationStatus.CANCELLED_BY_ADMIN);

        Reservation afterUpdate = reservationService.findById(beforeUpdate.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(afterUpdate.getReservationStatus(),ReservationStatus.CANCELLED_BY_ADMIN);
    }

    @Test
    public void testUpdateStatusAndConfirmationDateById(){
        Reservation beforeUpdate = testReservation;

        LocalDate confirmationDate = LocalDate.now();

        reservationService.create(beforeUpdate);
        reservationService.updateStatusAndConfirmationDateById(
                beforeUpdate.getId(),ReservationStatus.CONFIRMED,confirmationDate);

        Reservation afterUpdate = reservationService.findById(beforeUpdate.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(afterUpdate.getReservationStatus(),ReservationStatus.CONFIRMED);
        assertEquals(afterUpdate.getAdminConfirmationDate(),confirmationDate);
    }

    @Test
    public void testUpdateIsPaidById(){
        Reservation beforeUpdate = testReservation;

        reservationService.create(beforeUpdate);

        reservationService.updateIsPaidById(beforeUpdate.getId(),true);

        Reservation afterUpdate = reservationService.findById(beforeUpdate.getId())
                .orElseThrow(IllegalStateException::new);

        assertTrue(afterUpdate.isPaid());
    }

    @Test
    public void testUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById1(){
        Reservation toUpdate = TestDatabase.testReservation5;
        Apartment updateWithApartment1 = TestDatabase.testApartment1;
        LocalDate confirmationDate = LocalDate.now();

        reservationService.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                toUpdate.getId(),updateWithApartment1.getId(),confirmationDate);

        Reservation afterUpdate = reservationService.findById(toUpdate.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(afterUpdate.getApartmentId(),updateWithApartment1.getId());
        assertEquals(afterUpdate.getApartmentPrice(),updateWithApartment1.getPrice());
        assertEquals(afterUpdate.getAdminConfirmationDate(),confirmationDate);
    }

    @Test
    public void testUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById2(){
        Reservation toUpdate = TestDatabase.testReservation5;
        Apartment updateWithApartment2 = TestDatabase.testApartment2;
        LocalDate confirmationDate = LocalDate.now();

        reservationService.updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
                toUpdate.getId(),updateWithApartment2.getId(),confirmationDate);

        Reservation afterUpdate = reservationService.findById(toUpdate.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(afterUpdate.getApartmentId(),updateWithApartment2.getId());
        assertEquals(afterUpdate.getApartmentPrice(),updateWithApartment2.getPrice());
        assertEquals(afterUpdate.getAdminConfirmationDate(),confirmationDate);
    }
}