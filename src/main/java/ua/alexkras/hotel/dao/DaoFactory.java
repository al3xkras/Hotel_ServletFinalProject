package ua.alexkras.hotel.dao;

import ua.alexkras.hotel.dao.impl.*;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract JDBCApartmentDao createApartmentDAO();
    public abstract JDBCPaymentDao createPaymentDAO();
    public abstract JDBCReservationDao createReservationDAO();
    public abstract JDBCUserDao createUserDAO();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
