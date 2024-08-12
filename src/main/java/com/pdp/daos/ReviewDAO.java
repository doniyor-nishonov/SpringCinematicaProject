package com.pdp.daos;

import com.pdp.domains.Review;
import com.pdp.dto.ReviewDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:55
 **/
@Component
public class ReviewDAO extends BaseDAO<Review, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected ReviewDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Review> rowMapper = (rs, rowNum) -> Review.builder()
            .id(rs.getInt("review_id"))
            .userId(rs.getInt("user_id"))
            .moveId(rs.getInt("move_id"))
            .rating(rs.getDouble("rating"))
            .comment(rs.getString("comment"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(Review entity) {
        var sql = "INSERT INTO reviews(user_id, movie_id, rating, comment) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"review_id"});
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getMoveId());
            statement.setDouble(3, entity.getRating());
            statement.setString(4, entity.getComment());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();

    }

    @Override
    public void update(Review entity) {
        var sql = "UPDATE reviews SET user_id=? AND movie_id=? AND rating=? AND comment=? WHERE review_id=?;";
        jdbcTemplate.update(sql, entity.getUserId(), entity.getMoveId(), entity.getRating(), entity.getComment(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE reviews SET is_active=false WHERE review_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Review findById(Integer uuid) {
        var sql = "SELECT * FROM reviews WHERE is_active AND review_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<Review> findAll() {
        var sql = "SELECT * FROM reviews WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<ReviewDTO> findAllByMovieId(Integer movieId) {
        var sql = """
                select *
                from reviews r
                         inner join users u on r.user_id = u.user_id
                        left join images i on i.image_id = u.image_id
                where r.movie_id = ?
                  and r.is_active;
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> ReviewDTO.builder()
                .fullName(rs.getString("name"))
                .comment(rs.getString("comment"))
                .rating(rs.getFloat("rating"))
                .reviewId(rs.getInt("review_id"))
                .userId(rs.getInt("user_id"))
                .movieId(rs.getInt("movie_id"))
                .extension(rs.getString("extension"))
                .build(), movieId);
    }
}
