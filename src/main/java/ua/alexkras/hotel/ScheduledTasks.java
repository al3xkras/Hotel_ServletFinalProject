package ua.alexkras.hotel;

import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

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
    private final ReservationServiceImpl reservationService = new ReservationServiceImpl();

    public void scheduleUpdateExpiredReservations() {
        reservationService.updateAllExpiredReservations();

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
