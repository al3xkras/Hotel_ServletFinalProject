package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.UserDAO;
import ua.alexkras.hotel.entity.User;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    //@Autowired

    public Optional<User> getUserById(long id) throws SQLException {
        return UserDAO.getUserById(id);
    }

    /**
     * Get user from a data source by username (login)
     *
     * @param username User's login (username)
     * @return Optional\<User>, if user was found, Optional.empty() otherwise.
     */
    public Optional<User> getUserByUserName(String username) throws SQLException {
        return UserDAO.getUserByUsername(username);
    }

    /**
     * Add new user to a data source
     * -Encode user's password, using HotelUserDetailsService.passwordEncoder(),
     *   before saving it to the data source
     *
     * @param user User to add
     * @throws RuntimeException if User was not added to the data source
     */
    public void addUser(User user){
        /*
        String notEncodedPassword=user.getPassword();
        boolean userWasAdded = false;
        try {
            user.setPassword(HotelUserDetailsService.passwordEncoder().encode(user.getPassword()));
            userDAO.save(user);
            userWasAdded=true;
        } catch (Exception e){
            user.setPassword(notEncodedPassword);
        }
        if (!userWasAdded){
            throw new RuntimeException("Error adding new "+user.toString());
        }
         */
    }


}
