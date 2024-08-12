package com.pdp.daos;

import com.pdp.domains.Movie;
import com.pdp.dto.MovieDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:54
 **/
@Component
public class MovieDAO extends BaseDAO<Movie, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected MovieDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Movie> rowMapper = (rs, rowNum) -> Movie.builder()
            .id(rs.getInt("movie_id"))
            .title(rs.getString("title"))
            .description(rs.getString("description"))
            .releaseDate(rs.getDate("release_date").toLocalDate())
            .rating(rs.getDouble("rating"))
            .trailerUrl(rs.getString("trailer_url"))
            .language(rs.getString("language"))
            .imageId(rs.getInt("image_id"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    private final RowMapper<MovieDTO> dtoRowMapper = (rs, rowNum) -> MovieDTO.builder()
            .movie_id(rs.getInt("movie_id"))
            .title(rs.getString("title"))
            .description(rs.getString("description"))
            .releaseDate(rs.getDate("release_date").toLocalDate())
            .rating(rs.getDouble("rating"))
            .trailerUrl(rs.getString("trailer_url"))
            .language(rs.getString("language"))
            .extension(rs.getString("extension"))
            .originalName(rs.getString("name"))
            .build();

    @Override
    public Integer save(Movie entity) {
        var sql = "INSERT INTO movies(title, description, release_date, trailer_url, language,image_id,rating) VALUES (?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"movie_id"});
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, Date.valueOf(entity.getReleaseDate()));
            statement.setString(4, entity.getTrailerUrl());
            statement.setString(5, entity.getLanguage());
            statement.setInt(6, entity.getImageId());
            statement.setDouble(7, entity.getRating());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Movie entity) {
        var sql = "UPDATE movies SET title=?  AND description=? AND release_date=? AND rating=? AND trailer_url=? WHERE movie_id=?;";
        jdbcTemplate.update(sql, entity.getTitle(), entity.getDescription(), entity.getReleaseDate(), entity.getRating(), entity.getTrailerUrl(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE movies SET is_active=false WHERE movie_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Movie findById(Integer uuid) {
        var sql = "SELECT * FROM movies WHERE is_active AND movie_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<Movie> findAll() {
        var sql = "SELECT * FROM movies WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<MovieDTO> findAllMoviesWithImages() {
        var sql = "select m.*,im.name,im.extension from movies m inner join images im on m.image_id = im.image_id where m.is_active;";
        return jdbcTemplate.query(sql, dtoRowMapper);
    }

    public List<MovieDTO> findAllMoviesByCategory(String selectedCategory) {
        var sql = """
                select *
                from movies m
                         inner join movie_category mc on m.movie_id = mc.move_id
                         inner join category c on mc.category_id = c.category_id
                         inner join images i on m.image_id = i.image_id
                where c.name=? and m.is_active;
                """;
        return jdbcTemplate.query(sql, dtoRowMapper, selectedCategory);
    }

    public List<MovieDTO> findAllByName(String name) {
        var sql = "select * from movies m inner join images i on m.image_id = i.image_id where m.is_active and m.title rlike ?;";
        return jdbcTemplate.query(sql, dtoRowMapper, name);
    }
}
