package ua.alexkras.hotel.service.impl;

import ua.alexkras.hotel.dao.impl.JDBCDaoFactory;
import ua.alexkras.hotel.dao.impl.JDBCUserDao;
import ua.alexkras.hotel.entity.User;
import ua.alexkras.hotel.model.Pageable;
import ua.alexkras.hotel.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService<Pageable,User> {

    private final JDBCUserDao userDao;

    public UserServiceImpl(){
        userDao = JDBCDaoFactory.getInstance().createUserDAO();
    }

    @Override
    public Optional<User> findById(long id){
        return userDao.findById(id);
    }

    /**
     * Get user from a data source by username (login)
     *
     * @param username User's login (username)
     * @return Optional\<User>, if user was found, Optional.empty() otherwise.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Add new user entity to a data source
     * @param user User to add
     * @throws RuntimeException if User was not added to the data source
     */
    @Override
    public void create(User user){
        userDao.create(user);
    }

    @Override
    public void commitCurrentTransaction(){
        userDao.commit();
    }

    @Override
    public void rollbackConnection(){
        userDao.rollback();
    }


}
