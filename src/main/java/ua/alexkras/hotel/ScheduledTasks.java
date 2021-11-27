package ua.alexkras.hotel;

import ua.alexkras.hotel.dao.impl.ConnectionPoolHolder;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.ReservationService;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ScheduledTasks implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private ServletContext servletContext;
    private final ReservationService<Pageable> reservationService = new ReservationServiceImpl();

    public void scheduleUpdateExpiredReservations() {
        Connection connection;
        try {
            connection = ConnectionPoolHolder.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            reservationService.updateAllExpiredReservations(connection);

            connection.commit();
            connection.close();
        } catch (Exception e){
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        servletContext.log("scheduled task executed");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(this::scheduleUpdateExpiredReservations, 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduler.shutdownNow();
    }
}
