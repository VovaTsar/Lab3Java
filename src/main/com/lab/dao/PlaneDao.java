package com.lab.dao;

import com.lab.entity.Plane;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class PlaneDao implements AutoCloseable {
    private EntityManager entityManager;

    public PlaneDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Plane> getAll() {
        //   List<Plane> listOfPlane = entityManager.createQuery("SELECT a FROM Plane a ").getResultList();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Plane> personCriteria = cb.createQuery(Plane.class);
        Root<Plane> personRoot = personCriteria.from(Plane.class);
        personCriteria.select(personRoot);
        List<Plane> listOfPlane = entityManager.createQuery(personCriteria).getResultList();
        return listOfPlane;
    }
    public  boolean create(Plane plane) {
        entityManager.getTransaction().begin();
        entityManager.persist(plane);
        if (entityManager.find(Plane.class, plane.getId()) != null) {
            entityManager.getTransaction().commit();
            return true;
        }
        entityManager.getTransaction().commit();
        return false;
    }
    public Optional<Plane> getById(long id) {
        entityManager.getTransaction().begin();
        Optional<Plane> car = Optional.of(entityManager.find(Plane.class, id));
        entityManager.getTransaction().commit();
        return car;
    }

    public boolean remove(long id) {
        try {
            entityManager.getTransaction().begin();
            Plane planeRemove = entityManager.find(Plane.class, id);
            entityManager.remove(planeRemove);
            entityManager.getTransaction().commit();
            return true;
        } catch (IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

//    public Plane findById(int id) {
//        entityManager.getTransaction().begin();
//        Plane plane = entityManager.find(Plane.class, id);
//        entityManager.getTransaction().commit();
//        return plane;
//    }

    public boolean update(Plane newPlane) {
        entityManager.getTransaction().begin();
        Optional<Plane> updatedCar = Optional.of(entityManager.merge(newPlane));

        if (updatedCar.get().equals(
                entityManager.find(Plane.class, newPlane.getId()))){
            entityManager.getTransaction().commit();
            return true;
        }
        entityManager.getTransaction().rollback();
        return false;
    }

    @Override
    public void close() {
        entityManager.close();
    }



}
