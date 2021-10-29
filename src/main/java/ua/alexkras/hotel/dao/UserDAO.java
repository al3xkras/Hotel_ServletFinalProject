package ua.alexkras.hotel.dao;


import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.MySqlConnection;
import ua.alexkras.hotel.model.MySqlStrings;
import ua.alexkras.hotel.model.UserType;

import java.sql.*;
import java.util.Optional;

public class UserDAO {
    public static Optional<User> getUserByUsername(String username){
        User user;
        try(Connection conn = MySqlConnection.get();
            PreparedStatement getUserByName = conn.prepareStatement(MySqlStrings.getUserByUsername);
            ){
            getUserByName.setString(1,username);
            ResultSet result = getUserByName.executeQuery();

            if (!result.next()){
                return Optional.empty();
            }

            user = User.builder()
                    .id(result.getLong("id"))
                    .name(result.getString("name"))
                    .surname(result.getString("surname"))
                    .username(username)
                    .password(result.getString("password"))
                    .birthday(result.getDate("birthday").toLocalDate())
                    .gender(result.getString("gender"))
                    .phoneNumber(result.getString("phone_number"))
                    .userType(UserType.valueOf(result.getString("user_type")))
                    .build();

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public static Optional<User> getUserById(long id) {
        User user;
        try(Connection conn = MySqlConnection.get();
            PreparedStatement getUserById = conn.prepareStatement(MySqlStrings.getUserById);
            ){
            getUserById.setLong(1,id);
            ResultSet result = getUserById.executeQuery();

            if (result.isBeforeFirst()){
                return Optional.empty();
            }
            result.next();

            user = User.builder()
                    .id(id)
                    .name(result.getString("name"))
                    .surname(result.getString("surname"))
                    .username(result.getString("username"))
                    .password(result.getString("password"))
                    .birthday(result.getDate("birthday").toLocalDate())
                    .gender(result.getString("gender"))
                    .phoneNumber(result.getString("phone_number"))
                    .userType(UserType.valueOf(result.getString("user_type")))
                    .build();

        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public static void addUser(long id, User user) {
        //PasswordEncoder encoder = HotelUserDetailsService.passwordEncoder();

        try(Connection conn = MySqlConnection.get();
            PreparedStatement addUserIfNotExists = conn.prepareStatement(MySqlStrings.addUser)
        ){

            addUserIfNotExists.setLong(1,id);
            addUserIfNotExists.setString(2, user.getName());
            addUserIfNotExists.setString(3, user.getSurname());
            addUserIfNotExists.setString(4, user.getUsername());
            addUserIfNotExists.setString(5, user.getPassword());
            addUserIfNotExists.setDate(6, Date.valueOf(user.getBirthday()));
            addUserIfNotExists.setString(7, user.getGender());
            addUserIfNotExists.setString(8, user.getPhoneNumber());
            addUserIfNotExists.setString(9, user.getUserType().name());

            addUserIfNotExists.execute();

        } catch (SQLIntegrityConstraintViolationException ignored){

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
