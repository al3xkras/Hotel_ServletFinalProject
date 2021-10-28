package ua.alexkras.hotel.model;


import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.exception.UsernameNotFoundException;

import java.sql.*;
import java.util.Optional;



public class HotelUserDetailsService {

    private static User currentUser;

    public static Optional<User> getUserDetailsFromUserByUsername(String username){
        if (currentUser !=null && currentUser.getUsername().equalsIgnoreCase(username)){
            return Optional.of(currentUser);
        }

        try(Connection conn = DriverManager.getConnection(MySqlStrings.connectionUrl, MySqlStrings.user, MySqlStrings.password);
            PreparedStatement getUsersByUsername = conn.prepareStatement(
                    String.format(MySqlStrings.sqlSelectColumnsFromUserDB,
                            String.join(", ",MySqlStrings.tableUserColumns))
                            +" WHERE " + MySqlStrings.colUserUsername + " = \"" + username + "\"" );){
            ResultSet users = getUsersByUsername.executeQuery();

            if (!users.isBeforeFirst()){
                return Optional.empty();
            }
            users.next();

            currentUser = User.builder()
                    .username(users.getString(MySqlStrings.colUserUsername))
                    .password(users.getString(MySqlStrings.colUserPassword))
                    .userType(UserType.USER)
                    .build();
        } catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(currentUser);
    }

    public static void clearCurrentUserDetails() {
        currentUser =null;
    }

    public static void updateCurrentUserDetails(String username) {
        currentUser = getUserDetailsFromUserByUsername(username).orElseThrow(IllegalStateException::new);
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetailsFromUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    //public static PasswordEncoder passwordEncoder(){
    //    return new BCryptPasswordEncoder(12);
    //}
}

