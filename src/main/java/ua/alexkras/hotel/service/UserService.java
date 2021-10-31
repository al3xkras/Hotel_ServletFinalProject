package ua.alexkras.hotel.service;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCUserDao;
import ua.alexkras.hotel.entity.User;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    //@Autowired
    private final JDBCUserDao userDao;

    public UserService(){
        userDao = JDBCDaoFactory.getInstance().createUserDAO();
    }

    public Optional<User> getUserById(long id) throws SQLException {
        return userDao.findById(id);
    }

    /**
     * Get user from a data source by username (login)
     *
     * @param username User's login (username)
     * @return Optional\<User>, if user was found, Optional.empty() otherwise.
     */
    public Optional<User> getUserByUserName(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Add new user to a data source
     * -Encode user's password, using HotelUserDetailsService.passwordEncoder(),
     *   before saving it to the data source
     *
     * @param user User to add
     * @throws RuntimeException if User was not added to the data source
     */
    public boolean addUser(User user){
        return userDao.create(user);
    }


}
