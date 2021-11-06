package ua.alexkras.hotel.service;

public interface Service {
    void commitCurrentTransaction();
    void rollbackConnection();
}
