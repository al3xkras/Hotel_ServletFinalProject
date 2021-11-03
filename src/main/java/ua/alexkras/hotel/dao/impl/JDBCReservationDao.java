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
        try(PreparedStatement addApartment = connection.prepareStatement(addReservation)
            ){
            addApartment.setLong(1,reservation.getUserId());

            Long apartmentId = reservation.getApartmentId();
            Integer apartmentPrice = reservation.getApartmentPrice();
            if (apartmentId!=null) {
                addApartment.setLong(2, apartmentId);
            } else {
                addApartment.setNull(2, Types.INTEGER);
            }
            addApartment.setString(3,reservation.getApartmentClass().name());
            addApartment.setInt(4,reservation.getPlaces());
            if (apartmentPrice!=null) {
                addApartment.setInt(5, apartmentPrice);
            } else {
                addApartment.setNull(5, Types.INTEGER);
            }
            addApartment.setString(6,reservation.getReservationStatus().name());
            addApartment.setDate(7,Date.valueOf(reservation.getFromDate()));
            addApartment.setDate(8,Date.valueOf(reservation.getToDate()));
            addApartment.setTimestamp(9,Timestamp.valueOf(reservation.getSubmitDate()));
            addApartment.setBoolean(10,reservation.isPaid());
            addApartment.setBoolean(11,reservation.isActive());
            addApartment.setBoolean(12,reservation.isExpired());

            addApartment.execute();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Optional<Reservation> findById(long reservationId){
        Reservation reservation;
        try(PreparedStatement getById = connection.prepareStatement(selectReservationById)){
            getById.setLong(1,reservationId);

            ResultSet resultSet = getById.executeQuery();
            if (!resultSet.next()){
                return Optional.empty();
            }

            reservation = getReservationFromResultSet(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(reservation);
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

    @Override
    public void updateReservationStatusById(long id, ReservationStatus reservationStatus){
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatusById)){

            preparedStatement.setString(1,reservationStatus.name());
            preparedStatement.setLong(2,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



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
                    connection.prepareStatement(selectActiveReservationsWithLimit)
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
            throw new RuntimeException();
        }
        return list;
    }

    @Override
    public List<Reservation> findPendingReservationsByUserId(long userId, int start, int total){
        if (total > requestMaxReservations){
            throw new IllegalArgumentException("Maximum reservations in request: "+requestMaxReservations
                    +", requested: "+total);
        }

        List<Reservation> list = new ArrayList<>();
        try(PreparedStatement findActiveByUserId=
                    connection.prepareStatement(selectActiveReservationsByUserIdWithLimit)
            ){


            findActiveByUserId.setLong(1,userId);
            findActiveByUserId.setInt(2,start-1);
            findActiveByUserId.setInt(3,total);


            ResultSet result=findActiveByUserId.executeQuery();

            while(result.next()){
                Reservation reservation = getReservationFromResultSet(result);
                list.add(reservation);
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    private Reservation getReservationFromResultSet(ResultSet resultSet) throws SQLException {
        Integer apartmentId = (Integer)resultSet.getObject(colApartmentId);
        return Reservation.builder()
                .id(resultSet.getInt(colReservationId))
                .userId(resultSet.getInt(colUserId))
                .apartmentId(apartmentId==null?null:Long.valueOf(apartmentId))
                .apartmentClass(ApartmentClass.valueOf(resultSet.getString(colApartmentClass)))
                .places(resultSet.getInt(colApartmentPlaces))
                .apartmentPrice((Integer)resultSet.getObject(colApartmentPrice))
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
