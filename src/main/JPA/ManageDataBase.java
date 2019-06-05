package JPA;

import entity.PlaneEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ManageDataBase {
    private static ManageDataBase manageDataBase;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private String persistenceUnitName = "NewPersistenceUnit";

    public ManageDataBase() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManager = entityManagerFactory.createEntityManager();
    }
    public static ManageDataBase getPlaneDAO() {
        if (manageDataBase == null)
            manageDataBase = new ManageDataBase();
        return manageDataBase;
    }

    public List<PlaneEntity> getAll() {
        //   List<PlaneEntity> listOfPlane = entityManager.createQuery("SELECT a FROM PlaneEntity a ").getResultList();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlaneEntity> personCriteria = cb.createQuery(PlaneEntity.class);
        Root<PlaneEntity> personRoot = personCriteria.from(PlaneEntity.class);
        personCriteria.select(personRoot);
        List<PlaneEntity> listOfPlane = entityManager.createQuery(personCriteria).getResultList();
        return listOfPlane;
    }

    public void remove(int id) {
        entityManager.getTransaction().begin();
        PlaneEntity planeRemove = entityManager.find(PlaneEntity.class, id);
        entityManager.remove(planeRemove);
        entityManager.getTransaction().commit();

    }

    public PlaneEntity findById(int id) {
        entityManager.getTransaction().begin();
        PlaneEntity planeEntity = entityManager.find(PlaneEntity.class, id);
        entityManager.getTransaction().commit();
        return planeEntity;
    }

    public void update(int id, PlaneEntity newPlane) {
        PlaneEntity previousPlane = entityManager.find(PlaneEntity.class, id);
        previousPlane.setBrand(newPlane.getBrand());
        previousPlane.setCaptain(newPlane.getCaptain());
        previousPlane.setEngine(newPlane.getEngine());
        previousPlane.setSeries(newPlane.getSeries());
        entityManager.getTransaction().begin();
        entityManager.merge(previousPlane);
        entityManager.getTransaction().commit();
    }

    public void persistEntity(PlaneEntity planeEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(planeEntity);
        entityManager.getTransaction().commit();
    }

}
