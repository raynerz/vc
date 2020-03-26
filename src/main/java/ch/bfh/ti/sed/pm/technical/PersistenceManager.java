package ch.bfh.ti.sed.pm.technical;

import ch.bfh.ti.sed.pm.domain.IEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersistenceManager extends ch.bfh.ti.sed.pm.domain.Persistence implements IEntityManager  {
    private EntityManager em;


    public void init(String persistenceUnitName) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.em = factory.createEntityManager();
    }
    
    /**
     * Begins the transaction, persists and commits
     *
     * @param entity entity instance
     */
    @Override
    public void persist(Object entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }
    
    /**
     * @param entityClass entity class
     * @param primaryKey  primary key
     * @param <T>         Type that we want to instantiate
     * @return An instance of the object that was found, null if not found
     */
    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        
        return em.find(entityClass, primaryKey);
    }
    
    /**
     * Creates an instance of TypedQuery for executing a Java Persistence query language statement. The select list of the query must contain only a single item, which must be assignable to the type specified by the resultClass argument.
     *
     * @param query       SQL Query
     * @param resultClass Class that we want to retrieve
     * @param <T>         Type of the resultClass
     * @return Lists of objects of the specified class
     * @throws IllegalArgumentException - if the query string is found to be invalid or if the query result is found to not be assignable to the specified type
     */
    @Override
    public <T> List<T> query(String query, Class<T> resultClass) throws IllegalArgumentException {
        TypedQuery<T> q = em.createQuery(query, resultClass);
        //        @SuppressWarnings(value = "unchecked")
        List<T> list = q.getResultList();
        return list;
    }
    
    /**
     * Closes the entity manager
     */
    @Override
    public void close() {
        em.close();
    }
    
}
