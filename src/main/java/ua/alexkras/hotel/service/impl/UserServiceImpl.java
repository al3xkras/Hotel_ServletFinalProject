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

    /**
     * Find user entity by id
     * @param id id of a user
     * @return Optional of user if present in a data source, Optional.empty() otherwise
     * @throws RuntimeException if an SQLException was caught when executing query
     */
    @Override
    public Optional<User> findById(long id){
        return userDao.findById(id);
    }

    /**
     * Get user from a data source by username (login)
     * @param username User's login (username)
     * @return Optional of User, if found, Optional.empty() otherwise.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Add new user entity to a data source
     * @param user user to add
     * @throws RuntimeException if @user is invalid (has null fields, that match not-null columns in the data source)
     * or any SQLException was caught when executing create statement
     */
    @Override
    public void create(User user){
        userDao.create(user);
    }

    /**
     * Commit current transaction (transactional connection),
     * - Committing a transaction from this service also
     *  commits methods that use transactional connection
     *  from other services
     * @throws RuntimeException if failed to commit transaction
     */
    @Override
    public void commitCurrentTransaction(){
        userDao.commit();
    }

    /**
     * Rollback transactional connection
     * - Rollback made from this service also
     *  affects methods that use transactional connection
     *  from other services (they will be discarded as well)
     * @throws RuntimeException if failed to rollback transactional connection
     */
    @Override
    public void rollbackConnection(){
        userDao.rollback();
    }


}
