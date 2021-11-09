package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.alexkras.hotel.dao.CreateTestDatabase;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;

import java.util.List;

import static org.junit.Assert.*;


public class ApartmentServiceTest {

    static Apartment testApartment4 = Apartment.builder()
            .id(4L)
            .name("test apartment 4")
            .places(2)
            .apartmentClass(ApartmentClass.ClassB)
            .status(ApartmentStatus.AVAILABLE)
            .price(6545)
            .build();


    static ApartmentServiceImpl apartmentService;

    Reservation matchReservation1 = CreateTestDatabase.matchReservation1;
    Reservation matchReservation2 = CreateTestDatabase.matchReservation2;

    @BeforeClass
    public static void beforeClass() {
        CreateTestDatabase.createTestDatabase();
        apartmentService = new ApartmentServiceImpl();
    }

    @Before
    public void beforeTest(){
        CreateTestDatabase.createTestDatabase();
    }

    @Test
    public void testCreateAndGetApartmentsCount() {
        assertEquals(3,apartmentService.getApartmentsCount());
        apartmentService.create(testApartment4);
        assertEquals(4,apartmentService.getApartmentsCount());
    }

    @Test
    public void testFindById() {

        Apartment testApartment1 = CreateTestDatabase.testApartment1;

        Apartment foundById = apartmentService
                .findById(testApartment1.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(foundById,testApartment1);
    }

    @Test
    public void testFindAllApartments() {

        Apartment testApartment1 = CreateTestDatabase.testApartment1;
        Apartment testApartment3 = CreateTestDatabase.testApartment3;

        Pageable pageable1 = new Pageable(2,apartmentService.getApartmentsCount());
        List<Apartment> found1 = apartmentService.findAllApartments(pageable1);

        assertEquals(2,found1.size());

        assertTrue(found1.contains(testApartment1));
        assertFalse(found1.contains(testApartment3));

        Pageable pageable2 = new Pageable(1,apartmentService.getApartmentsCount());
        List<Apartment> found2 = apartmentService.findAllApartments(pageable2);

        assertEquals(1,found2.size());

        Pageable pageable3 = new Pageable(10,apartmentService.getApartmentsCount());
        List<Apartment> found3 = apartmentService.findAllApartments(pageable3);

        assertEquals(3,found3.size());
    }

    @Test
    public void testGetApartmentsMatchingReservationCount(){
        assertEquals(1,apartmentService.getApartmentsMatchingReservationCount(matchReservation1));
        assertEquals(0,apartmentService.getApartmentsMatchingReservationCount(matchReservation2));
    }

    @Test
    public void testFindApartmentsMatchingReservation() {
        Apartment testApartment2 = CreateTestDatabase.testApartment2;

        assertEquals(3,apartmentService.getApartmentsCount());

        Pageable pageable1 = new Pageable(10,
                apartmentService.getApartmentsMatchingReservationCount(matchReservation1));

        Pageable pageable2 = new Pageable(0,
                apartmentService.getApartmentsMatchingReservationCount(matchReservation1));

        List<Apartment> matching1 = apartmentService.findApartmentsMatchingReservation(
                matchReservation1,pageable1);

        List<Apartment> matching2 = apartmentService.findApartmentsMatchingReservation(
                matchReservation1,pageable2);

        assertEquals(1,matching1.size());
        assertEquals(matching1.get(0),testApartment2);

        assertEquals(0,matching2.size());
    }

    @Test
    public void testUpdateApartment(){

        Apartment testApartment1 = CreateTestDatabase.testApartment1;
        apartmentService.create(testApartment1);

        Apartment testUpdate = Apartment.builder()
                .id(testApartment1.getId())
                .name("test apartment 1 update")
                .places(1)
                .apartmentClass(ApartmentClass.ClassD)
                .status(ApartmentStatus.OCCUPIED)
                .price(54543)
                .build();

        apartmentService.updateApartment(testUpdate);

        Apartment afterUpdate = apartmentService
                .findById(testApartment1.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(testUpdate,afterUpdate);
    }

    @Test
    public void testUpdateApartmentStatusById() {
        Apartment testApartment1 = CreateTestDatabase.testApartment1;

        Apartment apartment1AfterUpdate = Apartment.builder()
                .id(testApartment1.getId())
                .name("test apartment 1")
                .places(3)
                .apartmentClass(ApartmentClass.ClassA)
                .status(ApartmentStatus.UNAVAILABLE)
                .price(1000)
                .build();

        apartmentService.updateApartmentStatusById(testApartment1.getId(),apartment1AfterUpdate.getStatus());

        Apartment afterUpdate = apartmentService
                .findById(testApartment1.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(apartment1AfterUpdate,afterUpdate);
    }
}