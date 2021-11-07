package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.ReservationDAO;
import ua.alexkras.hotel.entity.Reservation;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.ReservationStatus;
import ua.alexkras.hotel.service.impl.ReservationServiceImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.alexkras.hotel.model.mysql.ApartmentTableStrings.selectApartmentByIdIntoVariable;
import static ua.alexkras.hotel.model.mysql.ReservationTableStrings.*;

public class JDBCReservationDao implements ReservationDAO {

    public static final int requestMaxReservations = 50;

    private final Connection connection;
    private final Connection transactional;

    public JDBCReservationDao(Connection connection, Connection transactional) {
        this.connection = connection;
        this.transactional = transactional;
    }

    @Override
    public void create(Reservation reservation){
        createInConnection(connection,reservation);
    }

    public void createInTransaction(Reservation reservation){
        createInConnection(transactional,reservation);
    }

    private void createInConnection(Connection connection, Reservation reservation){
        try(PreparedStatement addApartment = connection.prepareStatement(addReservation)){
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
            throw new RuntimeException();
        }
    }

    public Optional<Reservation> findById(long reservationId){
        Reservation reservation;
        try(PreparedStatement getById = connection.prepareStatement(selectReservationById)){
            getById.setLong(1,reservationId);

            ResultSet resultSet = getById.executeQuery();
            if (!resultSet.next()){
                return Optional.empty();
            }

            reservation = getReservationFromResultSet(resultSet)
                    .withCalculatedDaysUntilExpiration();

        } catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(reservation);
    }

    @Override
    public List<Reservation> findAllByActiveAndStatus(boolean isActive,ReservationStatus reservationStatus, int start, int total){
        if (total > requestMaxReservations){
            throw new IllegalArgumentException("Maximum reservations in request: "+requestMaxReservations
                    +", requested: "+total);
        }

        List<Reservation> list = new ArrayList<>();
        try(PreparedStatement select = connection.prepareStatement(selectActiveReservationsByStatusWithLimit)
        ){

            select.setBoolean(1,isActive);
            select.setString(2,reservationStatus.name());
            select.setInt(3,start-1);
            select.setInt(4,total);

            ResultSet result=select.executeQuery();

            while(result.next()){
                Reservation reservation = getReservationFromResultSet(result);
                list.add(reservation.withCalculatedDaysUntilExpiration());
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    @Override
    public List<Reservation> findByUserIdAndActiveAndAnyStatusExcept(
            long userId,
            boolean isActive,
            ReservationStatus illegalStatus,
            int start, int total){

        if (total > requestMaxReservations){
            throw new IllegalArgumentException("Maximum reservations in request: "+requestMaxReservations
                    +", requested: "+total);
        }

        List<Reservation> list = new ArrayList<>();
        try(PreparedStatement select=
                    connection.prepareStatement(selectActiveReservationsByUserIdAndAnyStatusExceptWithLimit)
        ){

            select.setBoolean(1,isActive);
            select.setLong(2,userId);
            select.setString(3,illegalStatus.name());
            select.setInt(4,start-1);
            select.setInt(5,total);

            ResultSet result=select.executeQuery();

            while(result.next()){
                Reservation reservation = getReservationFromResultSet(result);
                list.add(reservation.withCalculatedDaysUntilExpiration());
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    @Override
    public List<Reservation> findAll(int start, int total) {
        return null;
    }


    public int getReservationsCountByUserIdAndActiveAndAnyStatusExcept(
            long userId, boolean isActive,
            ReservationStatus illegalStatus){

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM hotel_db_servlet.reservations " +
                " WHERE user_id=? and is_active=? and reservation_status!=?")){

            preparedStatement.setLong(1,userId);
            preparedStatement.setBoolean(2,isActive);
            preparedStatement.setString(3,illegalStatus.name());

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return  rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public int getReservationsCountByActiveAndStatus(
            boolean isActive,
            ReservationStatus status){

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM hotel_db_servlet.reservations " +
                " WHERE is_active=? and reservation_status=?")){

            preparedStatement.setBoolean(1,isActive);
            preparedStatement.setString(2,status.name());

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return  rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Reservation getReservationFromResultSet(ResultSet resultSet) throws SQLException {
        Integer apartmentId = (Integer)resultSet.getObject(colApartmentId);
        Date adminConfirmationDate = resultSet.getDate(colAdminConfirmationDate);

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
                .adminConfirmationDate(adminConfirmationDate==null?null:adminConfirmationDate.toLocalDate())
                .isPaid(resultSet.getBoolean(colIsPaid))
                .isActive(resultSet.getBoolean(colIsActive))
                .expired(resultSet.getBoolean(colIsExpired))
                .build();

    }

    @Override
    public void update(Reservation entity) {

    }

    @Override
    public void transactionalUpdateReservationStatusById(long id, ReservationStatus reservationStatus){
        try (PreparedStatement preparedStatement = transactional.prepareStatement(updateStatusById)){

            preparedStatement.setString(1,reservationStatus.name());
            preparedStatement.setLong(2,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long reservationId, long apartmentId, LocalDate confirmationDate){

        updateReservationApartmentDataAndConfirmationDateByIdWithApartmentByIdInConnection(
                connection,reservationId,apartmentId,confirmationDate);
    }

    public void transactionalUpdateReservationApartmentDataAndConfirmationDateByIdWithApartmentById(
            long reservationId, long apartmentId, LocalDate confirmationDate){

        updateReservationApartmentDataAndConfirmationDateByIdWithApartmentByIdInConnection(
                transactional,reservationId,apartmentId,confirmationDate);
    }

    private void updateReservationApartmentDataAndConfirmationDateByIdWithApartmentByIdInConnection(
            Connection connection,long reservationId, long apartmentId, LocalDate confirmationDate){
        try (PreparedStatement getApartmentIntoVariable = connection.prepareStatement(selectApartmentByIdIntoVariable);
             PreparedStatement updateReservation = connection.prepareStatement(updateReservationByIdAndUserIdWithApartment)

        ){
            getApartmentIntoVariable.setLong(1,apartmentId);

            updateReservation.setDate(1,Date.valueOf(confirmationDate));
            updateReservation.setString(2,ReservationStatus.CONFIRMED.name());
            updateReservation.setLong(3,reservationId);

            getApartmentIntoVariable.execute();
            updateReservation.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void updateIsPaidById(long id, boolean isPaid){
        updateIsPaidByIdInConnection(connection,id,isPaid);
    }

    public void transactionalUpdateIsPaidById(long id, boolean isPaid){
        updateIsPaidByIdInConnection(transactional,id,isPaid);
    }

    private void updateIsPaidByIdInConnection(Connection connection, long id, boolean isPaid){
        try (PreparedStatement updateIsPaid = connection.prepareStatement("UPDATE hotel_db_servlet.reservations SET " +
                "id_paid=? WHERE id=?")){
            updateIsPaid.setBoolean(1,isPaid);
            updateIsPaid.setLong(2,id);

            updateIsPaid.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void updateAllExpiredReservations(){
        try (PreparedStatement updateExpired = transactional.prepareStatement("UPDATE " +
                     "hotel_db_servlet.reservations SET " +
                     "reservation_status=?,"+
                     "is_expired=true "+
                     "WHERE not is_expired and not id_paid and " +
                     "confirmation_date is not null and " +
                     "DATEDIFF(confirmation_date,?)>=?");
             PreparedStatement setExpiredReservationApartmentsAvailable = transactional.prepareStatement("UPDATE " +
                     "hotel_db_servlet.apartments SET "+
                     "status=? "+
                     "WHERE status='"+ ApartmentStatus.RESERVED+"' and "+
                     "id IN (SELECT apartment_id FROM hotel_db_servlet.reservations WHERE is_expired and is_active)");
             PreparedStatement updateActive = transactional.prepareStatement("UPDATE " +
                     "hotel_db_servlet.reservations SET " +
                     "is_active=false "+
                     "WHERE is_active and is_expired ")
        ){
            updateExpired.setString(1,ReservationStatus.CANCELLED.name());
            updateExpired.setDate(2, Date.valueOf(LocalDate.now()));
            updateExpired.setLong(3, ReservationServiceImpl.daysToCancelPayment);


            setExpiredReservationApartmentsAvailable.setString(1,ApartmentStatus.AVAILABLE.name());

            updateExpired.executeUpdate();
            setExpiredReservationApartmentsAvailable.executeUpdate();
            updateActive.executeUpdate();

            commit();
        } catch (SQLException e){
            rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void updateStatusAndConfirmationDateById(int id, ReservationStatus status, LocalDate confirmationDate){
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatusAndConfirmationDateById)){

            preparedStatement.setString(1,status.name());
            preparedStatement.setDate(2,Date.valueOf(confirmationDate));
            preparedStatement.setLong(3,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void commit(){
        try {
            transactional.commit();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void rollback(){
        try {
            transactional.rollback();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
