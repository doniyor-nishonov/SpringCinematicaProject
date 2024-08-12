package com.pdp.daos;

import com.pdp.domains.BaseDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:46
 **/

/**
 * Abstract base class for Data Access Objects (DAO) that provides generic methods for
 * interacting with the database. This class uses Spring's {@link JdbcTemplate} for
 * executing SQL queries and updates.
 *
 * @param <T>  The type of the domain entity that this DAO handles. It must extend
 *             {@link BaseDomain}.
 * @param <ID> The type of the entity's identifier. It must be serializable.
 * @see JdbcTemplate
 * @see BaseDomain
 */
@Component
public abstract class BaseDAO<T extends BaseDomain, ID extends Serializable> {

    protected final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    @Autowired
    protected BaseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves the given entity to the database.
     *
     * @param entity the entity to be saved.
     * @return the identifier of the saved entity.
     */
    public abstract ID save(T entity);

    /**
     * Updates the given entity in the database.
     *
     * @param entity the entity to be updated.
     */
    public abstract void update(T entity);

    /**
     * Deletes the entity with the specified identifier from the database.
     *
     * @param id the identifier of the entity to be deleted.
     */
    public abstract void delete(ID id);

    /**
     * Finds and returns the entity with the specified identifier.
     *
     * @param id the identifier of the entity to be found.
     * @return the entity with the specified identifier, or {@code null} if not found.
     */
    public abstract T findById(ID id);

    /**
     * Finds and returns all entities from the database.
     *
     * @return a list of all entities.
     */
    public abstract List<T> findAll();
}

