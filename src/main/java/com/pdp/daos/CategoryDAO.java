package com.pdp.daos;

import com.pdp.domains.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 05/August/2024  23:40
 **/
@Component
public class CategoryDAO extends BaseDAO<Category, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected CategoryDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Category> rowMapper = (rs,rowNum)->Category.builder()
            .id(rs.getInt("category_id"))
            .name(rs.getString("name"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(Category entity) {
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("insert into category (name) values (?);", new String[]{"category_id"});
            statement.setString(1, entity.getName());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(Integer uuid) {

    }

    @Override
    public Category findById(Integer uuid) {
        return jdbcTemplate.queryForObject("select * from category where category_id=?;", rowMapper,uuid );
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from category where is_active;", rowMapper);
    }
}
