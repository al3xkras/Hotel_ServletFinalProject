package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.ApartmentDao;
import ua.alexkras.hotel.entity.Apartment;
import ua.alexkras.hotel.model.ApartmentClass;
import ua.alexkras.hotel.model.ApartmentStatus;
import ua.alexkras.hotel.model.mysql.ApartmentTableStrings;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.alexkras.hotel.model.mysql.ApartmentTableStrings.*;

public class JDBCApartmentDao implements ApartmentDao {

    private final Connection connection;
    private final Connection transactional;

    public JDBCApartmentDao(Connection connection, Connection transactional) {
        this.connection = connection;
        this.transactional = transactional;
    }

    //@Transactional
    @Override
    public void updateApartmentStatusById(long id, ApartmentStatus apartmentStatus){
        try (PreparedStatement updateStatusById = transactional.prepareStatement(updateApartmentStatusById)
            ){
            updateStatusById.setString(1,apartmentStatus.name());
            updateStatusById.setLong(2,id);

            updateStatusById.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Apartment> findApartmentsByApartmentClassAndPlacesAndStatus(
            ApartmentClass apartmentClass, int places, ApartmentStatus apartmentStatus,
            int start, int total){

        ArrayList<Apartment> apartments = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(findByApartmentClassAndPlacesAndStatus);
            ){
            statement.setString(1,apartmentClass.name());
            statement.setInt(2,places);
            statement.setString(3,apartmentStatus.name());
            statement.setInt(4,start-1);
            statement.setInt(5,total);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Apartment apartment = getApartmentFromResultSet(rs);
                apartments.add(apartment);
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return apartments;
    }

    @Override
    public void create(Apartment apartment){
        try(PreparedStatement addApartment = connection.prepareStatement(ApartmentTableStrings.addApartment)
            ){
            addApartment.setString(1,apartment.getName());
            addApartment.setInt(2,apartment.getPlaces());
            addApartment.setString(3,apartment.getApartmentClass().name());
            addApartment.setString(4,apartment.getStatus().name());
            addApartment.setInt(5,apartment.getPrice());

            addApartment.execute();
        } catch (SQLIntegrityConstraintViolationException ignored){

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Apartment> findById(long id) {
        Apartment apartment;
        try(PreparedStatement findApartmentById = connection.prepareStatement(findById)
            ){
            findApartmentById.setLong(1,id);
            ResultSet result = findApartmentById.executeQuery();

            if (!result.next()){
                return Optional.empty();
            }

            apartment = getApartmentFromResultSet(result);

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(apartment);
    }

    @Override
    public List<Apartment> findAll(int start, int total) {
        ArrayList<Apartment> apartments = new ArrayList<>();

        try(PreparedStatement findAllApartments = connection.prepareStatement(ApartmentTableStrings.findAllApartments)
        ){
            ResultSet allApartments = findAllApartments.executeQuery();

            while(allApartments.next()){
                Apartment apartment = getApartmentFromResultSet(allApartments);
                apartments.add(apartment);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

        return apartments;
    }

    public List<Apartment> findApartments(int start, int total){

        List<Apartment> list = new ArrayList<>();
        try(PreparedStatement findApartments=
                    connection.prepareStatement(selectApartmentsWithLimit)
                ){

            findApartments.setInt(1,start-1);
            findApartments.setInt(2,total);

            ResultSet result=findApartments.executeQuery();

            while(result.next()){
                Apartment apartment = getApartmentFromResultSet(result);
                list.add(apartment);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void update(Apartment apartment) {
        updateInConnection(connection,apartment);
    }

    public void transactionalUpdateApartment(Apartment apartment){
        updateInConnection(transactional,apartment);
    }

    private void updateInConnection(Connection connection, Apartment apartment){
        if (apartment.getId()==null){
            throw new IllegalStateException();
        }

        try(PreparedStatement updateApartment = connection.prepareStatement(ApartmentTableStrings.updateApartment)
        ){
            updateApartment.setString(1, apartment.getName());
            updateApartment.setInt(2, apartment.getPlaces());
            updateApartment.setString(3,apartment.getApartmentClass().name());
            updateApartment.setString(4,apartment.getStatus().name());
            updateApartment.setInt(5,apartment.getPrice());
            updateApartment.setLong(6,apartment.getId());

            updateApartment.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteApartment = connection.prepareStatement(deleteApartmentById)
            ){
            deleteApartment.setLong(1,id);
            deleteApartment.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception e){
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

    private Apartment getApartmentFromResultSet(ResultSet resultSet) throws SQLException {
        return Apartment.builder()
                .id(resultSet.getLong(colApartmentId))
                .name(resultSet.getString(colApartmentName))
                .places(resultSet.getInt(colApartmentPlaces))
                .apartmentClass(ApartmentClass.valueOf(resultSet.getString(colApartmentClass)))
                .status(ApartmentStatus.valueOf(resultSet.getString(colApartmentStatus)))
                .price(resultSet.getInt(colApartmentPrice))
                .build();
    }
}
