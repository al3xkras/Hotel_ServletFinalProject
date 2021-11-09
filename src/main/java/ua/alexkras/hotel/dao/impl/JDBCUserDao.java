package ua.alexkras.hotel.dao.impl;

import ua.alexkras.hotel.dao.UserDAO;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.mysql.UserTableStrings;
import ua.alexkras.hotel.model.UserType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDAO {

    private final Connection connection;
    private final Connection transactional;

    public JDBCUserDao(Connection connection, Connection transactional) {
        this.connection = connection;
        this.transactional = transactional;
    }

    @Override
    public Optional<User> findByUsername(String username){
        User user;
        try(PreparedStatement getUserByName = connection.prepareStatement(UserTableStrings.findByUsername)
            ){
            getUserByName.setString(1,username);
            ResultSet result = getUserByName.executeQuery();

            if (!result.next()){
                return Optional.empty();
            }

            user = getUserFromResultSet(result);

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public void create(User user) {
        try(PreparedStatement addUserIfNotExists = connection.prepareStatement(UserTableStrings.addUser)
            ){
            prepareUserStatement(user, addUserIfNotExists);
            addUserIfNotExists.execute();

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void prepareUserStatement(User user, PreparedStatement addUserIfNotExists) throws SQLException {
        if (user.getId()==null){
            addUserIfNotExists.setNull(1,Types.INTEGER);
        } else {
            addUserIfNotExists.setLong(1,user.getId());
        }
        addUserIfNotExists.setString(2, user.getName());
        addUserIfNotExists.setString(3, user.getSurname());
        addUserIfNotExists.setString(4, user.getUsername());
        addUserIfNotExists.setString(5, user.getPassword());
        addUserIfNotExists.setDate(6, Date.valueOf(user.getBirthday()));
        addUserIfNotExists.setString(7, user.getGender());
        addUserIfNotExists.setString(8, user.getPhoneNumber());
        addUserIfNotExists.setString(9, user.getUserType().name());
    }

    @Override
    public Optional<User> findById(long id) {
        User user;
        try(PreparedStatement getUserById = connection.prepareStatement(UserTableStrings.findById)
            ){
            getUserById.setLong(1,id);
            ResultSet result = getUserById.executeQuery();

            if (result.isBeforeFirst()){
                return Optional.empty();
            }
            result.next();

            user = getUserFromResultSet(result);

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public List<User> findAll(int start, int total) {
        ArrayList<User> users = new ArrayList<>();

        try(PreparedStatement getAllUsers = connection.prepareStatement(UserTableStrings.findAllUsers)
            ){
            ResultSet allUsers = getAllUsers.executeQuery();

            while(allUsers.next()){
                User user = getUserFromResultSet(allUsers);
                users.add(user);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

        return users;
    }

    @Override
    public void update(User user) {
        if (user.getId()==null){
            throw new IllegalStateException();
        }

        try(PreparedStatement updateUser = connection.prepareStatement(UserTableStrings.updateUser)
            ){

            prepareUserStatement(user, updateUser);
            updateUser.setLong(9,user.getId());

            updateUser.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteUser = connection.prepareStatement(UserTableStrings.deleteUserById)
            ){

            deleteUser.setLong(1,id);
            deleteUser.execute();
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

    private User getUserFromResultSet(ResultSet userResultSet) throws SQLException {
        return User.builder()
                .id(userResultSet.getLong("id"))
                .name(userResultSet.getString("name"))
                .surname(userResultSet.getString("surname"))
                .username(userResultSet.getString("username"))
                .password(userResultSet.getString("password"))
                .birthday(userResultSet.getDate("birthday").toLocalDate())
                .gender(userResultSet.getString("gender"))
                .phoneNumber(userResultSet.getString("phone_number"))
                .userType(UserType.valueOf(userResultSet.getString("user_type")))
                .build();
    }
}
