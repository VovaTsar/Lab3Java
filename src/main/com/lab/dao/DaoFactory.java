package com.lab.dao;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private EntityManagerFactory entityManagerFactory;

    private DaoFactory() {
        entityManagerFactory= Persistence.createEntityManagerFactory
                ("NewPersistenceUnit");
//        Map<String, Object> configOverrides = new HashMap<String, Object>();
//        configOverrides.put("hibernate.hbm2ddl.auto", "create-drop");
//        entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit", configOverrides);
    }

    private static class SingletonDaoFactory {
        private final static DaoFactory daoFactory = new DaoFactory();
    }

    public static DaoFactory getInstance() {
        return SingletonDaoFactory.daoFactory;
    }

    public PlaneDao getPlaneDao() {
        return new PlaneDao(
                entityManagerFactory.createEntityManager());
    }

    public UserDao getUserDao() {
        return new UserDao(
                entityManagerFactory.createEntityManager());
    }

}