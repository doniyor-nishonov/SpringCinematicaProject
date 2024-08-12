package com.pdp.daos;

import com.pdp.domains.Cinema;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:44
 **/
@Component
public class CinemaDAO extends BaseDAO<Cinema, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected CinemaDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Cinema> rowMapper = (rs, rowNum) -> Cinema.builder()
            .id(rs.getInt("cinema_id"))
            .name(rs.getString("name"))
            .location(rs.getString("location"))
            .numberOfScreen(rs.getInt("number_of_screens"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(Cinema entity) {
        var sql = "INSERT INTO cinemas(name, location, number_of_screens) VALUES (?,?,?)";
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"cinema_id"});
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLocation());
            statement.setInt(3, entity.getNumberOfScreen());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Cinema entity) {
        var sql = "UPDATE cinemas SET name=? AND location=? AND number_of_screens=? WHERE cinema_id=?";
        jdbcTemplate.update(sql, entity.getName(), entity.getLocation(), entity.getNumberOfScreen(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE cinemas SET is_active=? WHERE cinema_id=?";
        jdbcTemplate.update(sql,uuid);
    }

    @Override
    public Cinema findById(Integer uuid) {
        var sql = "SELECT * FROM cinemas WHERE is_active AND cinema_id=?;";
        return jdbcTemplate.queryForObject(sql,rowMapper,uuid);
    }

    @Override
    public List<Cinema> findAll() {
        var sql = "SELECT * FROM cinemas WHERE is_active;";
        return jdbcTemplate.query(sql,rowMapper);
    }
}
