package ch.bfh.ti.sed.pm.domain;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import java.util.List;

public interface IEntityManager {


    /**
     * Comments source: https://github.com/javaee/jpa-spec/blob/master/javax.persistence-api/src/main/java/javax/persistence/EntityManager.java
     *
     * // TODO Manage the exceptions in the technical layer so we do not depend on the javax.persistance.* package
     */

    /**
     * Make an instance managed and persistent.
     * @param entity  entity instance
     * @throws EntityExistsException if the entity already exists.
     * (If the entity already exists, the <code>EntityExistsException</code> may
     * be thrown when the persist operation is invoked, or the
     * <code>EntityExistsException</code> or another <code>PersistenceException</code> may be
     * thrown at flush or commit time.)
     * @throws IllegalArgumentException if the instance is not an
     *         entity
     * @throws TransactionRequiredException if there is no transaction when
     *         invoked on a container-managed entity manager of that is of type
     *         <code>PersistenceContextType.TRANSACTION</code>
     */
    public void persist(Object entity);

    /**
     * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context,
     * it is returned from there.
     * @param entityClass  entity class that we want to find in the database
     * @param <T> type of the entity class
     * @param primaryKey  primary key
     * @return the found entity instance or null if the entity does
     *         not exist (Returns as Object)
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entity's primary key or
     *         is null
     */
    public <T> T find(Class<T> entityClass, Object primaryKey) throws IllegalArgumentException;

    /**
     * Method used for querying the database for a specific type of object
     * @param query SQL Query
     * @param resultClass Class that we want to retrieve
     * @param <T> Type of the resultClass
     * @return A list of the objects from the specified classs
     */
    public <T> List<T> query(String query, Class<T> resultClass) throws IllegalArgumentException;
    
  
    public void close();


    void init(String pm);
}
