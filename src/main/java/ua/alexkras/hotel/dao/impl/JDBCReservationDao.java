package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.ReservationDAO;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ReservationStatus;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.alexkras.hotel.model.mysql.ReservationTableStrings.*;

public class JDBCReservationDao implements ReservationDAO {

    public static final int requestMaxReservations = 50;

    private final Connection connection;

    public JDBCReservationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Reservation> findByUserId(long userId){
        return null;
    }

    @Override
    public List<Reservation> findByUserIdAndIsActive(long userId, boolean isActive){
        return null;
    }

    //@Query("update Reservation reservation set reservation.isActive =:isActive where reservation.userId =:userId")
    @Override
    public void updateActiveByUserId(long userId, boolean isActive){

    }

    @Override
    public boolean create(Reservation reservation){
        try(PreparedStatement addApartment = connection.prepareStatement(addReservation);
            ){
            addApartment.setLong(1,reservation.getUserId());
            addApartment.setLong(2,reservation.getApartmentId());
            addApartment.setString(3,reservation.getApartmentClass().name());
            addApartment.setInt(4,reservation.getPlaces());
            addApartment.setInt(5,reservation.getApartmentPrice());
            addApartment.setString(6,reservation.getReservationStatus().name());
            addApartment.setDate(7,Date.valueOf(reservation.getFromDate()));
            addApartment.setDate(8,Date.valueOf(reservation.getToDate()));
            addApartment.setTimestamp(9,Timestamp.valueOf(reservation.getSubmitDate()));
            addApartment.setDate(10,Date.valueOf(reservation.getAdminConfirmationDate()));
            addApartment.setBoolean(11,reservation.isPaid());
            addApartment.setBoolean(12,reservation.isActive());
            addApartment.setBoolean(13,reservation.isExpired());

            addApartment.execute();
        } catch (SQLIntegrityConstraintViolationException ignored){

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Optional<Reservation> findById(long reservationId){
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public void update(Reservation entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }

    @Override
    public List<Reservation> findByReservationStatus(ReservationStatus reservationStatus){
        return null;
    }

    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus where reservation.id =:id")
    @Override
    public void updateReservationStatusById(long id, ReservationStatus reservationStatus){

    }


    //@Query("update Reservation reservation set reservation.reservationStatus =:reservationStatus, " +
    //         "reservation.adminConfirmationDate =:confirmationDate where reservation.id =:id")
    @Override
    public void updateReservationStatusAndConfirmationDateById(
            long id, ReservationStatus reservationStatus, LocalDate confirmationDate){

    }
    /*
    @Query("update Reservation reservation set " +
            "reservation.apartmentId =:apartmentId, reservation.apartmentPrice =:apartmentPrice, " +
            "reservation.reservationStatus =:reservationStatus, " +
            "reservation.adminConfirmationDate =:confirmationDate  where reservation.id =:reservationId")

     */
    @Override
    public void updateApartmentIdAndPriceAndReservationStatusAndConfirmationDateById(
            long apartmentId,
            int apartmentPrice,
            ReservationStatus reservationStatus,
            LocalDate confirmationDate,
            int reservationId){

    }


    //@Query("update Reservation reservation set reservation.isPaid =:isPaid where reservation.id =:id")
    @Override
    public void updateIsPaidById(long id, boolean isPaid){

    }

    @Override
    public List<Reservation> findAllPendingReservations(int start, int total){
        if (total > requestMaxReservations){
            throw new IllegalArgumentException("Maximum reservations in request: "+requestMaxReservations
                +", requested: "+total);
        }

        List<Reservation> list = new ArrayList<>();
        try(PreparedStatement getPendingReservations=
                    connection.prepareStatement(selectPendingReservationsWithLimit)
            ){

            getPendingReservations.setInt(1,start-1);
            getPendingReservations.setInt(2,total);

            ResultSet result=getPendingReservations.executeQuery();

            while(result.next()){
                Reservation reservation = getReservationFromResultSet(result);
                list.add(reservation);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Reservation> findPendingReservationsByUserId(int userId, int start, int total){
        if (total > requestMaxReservations){
            throw new IllegalArgumentException("Maximum reservations in request: "+requestMaxReservations
                    +", requested: "+total);
        }

        List<Reservation> list = new ArrayList<>();
        try(PreparedStatement findActiveByUserId=
                    connection.prepareStatement(selectPendingReservationsByUserIdWithLimit)
            ){

            findActiveByUserId.setInt(1,start-1);
            findActiveByUserId.setInt(2,total);
            findActiveByUserId.setInt(3,userId);

            ResultSet result=findActiveByUserId.executeQuery();

            while(result.next()){
                Reservation reservation = getReservationFromResultSet(result);
                list.add(reservation);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    private Reservation getReservationFromResultSet(ResultSet resultSet) throws SQLException {
        return Reservation.builder()
                .id(resultSet.getInt(colReservationId))
                .userId(resultSet.getInt(colUserId))
                .apartmentId(resultSet.getInt(colApartmentId))
                .apartmentClass(ApartmentClass.valueOf(resultSet.getString(colApartmentClass)))
                .places(resultSet.getInt(colApartmentPlaces))
                .apartmentPrice(resultSet.getInt(colApartmentPrice))
                .reservationStatus(ReservationStatus.valueOf(resultSet.getString(colReservationStatus)))
                .fromDate(resultSet.getDate(colFromDate).toLocalDate())
                .toDate(resultSet.getDate(colToDate).toLocalDate())
                .submitDate(resultSet.getTimestamp(colSubmitDate).toLocalDateTime())
                .adminConfirmationDate(resultSet.getDate(colFromDate).toLocalDate())
                .isPaid(resultSet.getBoolean(colIsPaid))
                .isActive(resultSet.getBoolean(colIsActive))
                .expired(resultSet.getBoolean(colIsExpired))
                .build();

    }
}
