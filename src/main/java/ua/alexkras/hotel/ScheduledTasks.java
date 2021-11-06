package ua.alexkras.hotel;

import ua.alexkras.hotel.service.ReservationService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ScheduledTasks implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private ServletContext servletContext;
    private final ReservationService reservationService = new ReservationService();

    public void scheduleUpdateExpiredReservations() {
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
