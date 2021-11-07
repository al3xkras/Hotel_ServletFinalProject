package ua.alexkras.hotel.service;

import junit.framework.TestCase;
import ua.alexkras.hotel.FirstLaunch;
import ua.alexkras.hotel.dao.ReservationDAO;

public class ReservationServiceTest extends TestCase {

    ReservationDAO reservationDAO;

    public void setUp() throws Exception {
        super.setUp();

        FirstLaunch.truncateAllTablesOfTestDatabase();
        ReservationDAO reservationDAO = JDBCDaoFactory.getInstance().createReservationDAO();

    }

    public void testCreate() {
    }

    public void testFindById() {
    }

    public void testFindByReservationStatus() {
    }

    public void testFindByUserIdAndActiveAndAnyStatusExcept() {
    }

    public void testGetReservationFullCost() {
    }

    public void testGetReservationsCountByUserIdAndActiveAndAnyStatusExcept() {
    }

    public void testGetReservationsCountByActiveAndStatus() {

    }

    public void testUpdateStatusById() {

    }

    public void testUpdateStatusAndConfirmationDateById() {

    }

    public void testUpdateIsPaidById() {

    }

    public void testUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById() {

    }

    public void testUpdateAllExpiredReservations() {

    }
}