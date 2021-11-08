package ua.alexkras.hotel.service;

import org.junit.Before;
import org.junit.Test;
import ua.alexkras.hotel.FirstLaunch;
import ua.alexkras.hotel.dao.ReservationDAO;

public class ReservationServiceTest {

    ReservationDAO reservationDAO;

    @Before
    public void setUp() {
        FirstLaunch.truncateAllTablesOfTestDatabase();

        ReservationDAO reservationDAO = JDBCDaoFactory.getInstance().createReservationDAO();
    }

    @Test
    public void testCreate(){


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