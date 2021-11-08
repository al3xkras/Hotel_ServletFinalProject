package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.Test;
import ua.alexkras.hotel.FirstLaunch;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.impl.ApartmentServiceImpl;

import java.util.List;

import static org.junit.Assert.*;


public class ApartmentServiceTest {

    Apartment testApartment1 = Apartment.builder()
            .id(1L)
            .name("test apartment 1")
            .places(3)
            .apartmentClass(ApartmentClass.ClassA)
            .status(ApartmentStatus.AVAILABLE)
            .price(1000)
            .build();

    Apartment testApartment2 = Apartment.builder()
            .id(2L)
            .name("test apartment 2")
            .places(2)
            .apartmentClass(ApartmentClass.ClassC)
            .status(ApartmentStatus.AVAILABLE)
            .price(1000)
            .build();

    Apartment testApartment3 = Apartment.builder()
            .name("test apartment 3")
            .places(2)
            .apartmentClass(ApartmentClass.ClassC)
            .status(ApartmentStatus.UNAVAILABLE)
            .price(1000)
            .build();

    Reservation matchReservation = Reservation.builder()
            .apartmentClass(ApartmentClass.ClassC)
            .places(2)
            .build();

    ApartmentServiceImpl apartmentService;
    @Before
    public void setUp() {
        FirstLaunch.truncateAllTablesOfTestDatabase();

        apartmentService = new ApartmentServiceImpl();
    }



    @Test
    public void testCreateAndGetApartmentsCount() {
        assertEquals(0,apartmentService.getApartmentsCount());
        apartmentService.create(testApartment1);
        assertEquals(1,apartmentService.getApartmentsCount());

        apartmentService.create(testApartment2);
        apartmentService.create(testApartment3);
        assertEquals(3,apartmentService.getApartmentsCount());

    }

    @Test
    public void testFindById() {
        apartmentService.create(testApartment1);
        apartmentService.create(testApartment2);
        apartmentService.create(testApartment3);

        Apartment foundById = apartmentService
                .findById(testApartment1.getId())
                .orElseThrow(IllegalStateException::new);

        assertEquals(foundById,testApartment1);


    }

    @Test
    public void testFindAllApartments() {
        apartmentService.create(testApartment1);
        apartmentService.create(testApartment2);
        apartmentService.create(testApartment3);

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
        apartmentService.create(testApartment1);

        assertEquals(0,apartmentService.getApartmentsMatchingReservationCount(matchReservation));

        apartmentService.create(testApartment2);

        assertEquals(1,apartmentService.getApartmentsMatchingReservationCount(matchReservation));

        apartmentService.create(testApartment3);

        assertEquals(1,apartmentService.getApartmentsMatchingReservationCount(matchReservation));
    }

    @Test
    public void testFindApartmentsMatchingReservation() {
        apartmentService.create(testApartment1);
        apartmentService.create(testApartment2);
        apartmentService.create(testApartment3);

        assertEquals(3,apartmentService.getApartmentsCount());

        Pageable pageable1 = new Pageable(10,
                apartmentService.getApartmentsMatchingReservationCount(matchReservation));

        Pageable pageable2 = new Pageable(0,
                apartmentService.getApartmentsMatchingReservationCount(matchReservation));

        List<Apartment> matching1 = apartmentService.findApartmentsMatchingReservation(
                matchReservation,pageable1);

        List<Apartment> matching2 = apartmentService.findApartmentsMatchingReservation(
                matchReservation,pageable2);

        assertEquals(1,matching1.size());
        assertEquals(matching1.get(0),testApartment2);

        assertEquals(0,matching2.size());
    }

    @Test
    public void testUpdateApartment(){

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
        apartmentService.create(testApartment1);

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