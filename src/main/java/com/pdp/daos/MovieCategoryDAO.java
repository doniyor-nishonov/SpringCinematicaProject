package com.pdp.daos;

import com.pdp.domains.MovieCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  00:08
 **/
@Component
public class MovieCategoryDAO extends BaseDAO<MovieCategory, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected MovieCategoryDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<MovieCategory> rowMapper = (rs, rowNum) -> MovieCategory.builder()
            .id(rs.getInt("movie_category_id"))
            .moveId(rs.getInt("move_id"))
            .categoryId(rs.getInt("category_id"))
            .build();

    @Override
    public Integer save(MovieCategory entity) {
        var sql = "insert into movie_category(move_id, category_id) values(?,?);";
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"movie_category_id"});
            statement.setInt(1, entity.getMoveId());
            statement.setInt(2, entity.getCategoryId());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(MovieCategory entity) {

    }

    @Override
    public void delete(Integer uuid) {

    }

    @Override
    public MovieCategory findById(Integer uuid) {
        return jdbcTemplate.queryForObject("select * from movie_category where move_category_id=?;",rowMapper,uuid);
    }

    @Override
    public List<MovieCategory> findAll() {
        return jdbcTemplate.query("select * from movie_category;",rowMapper);
    }
}
