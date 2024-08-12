package com.pdp.daos;

import com.pdp.domains.Showtime;
import com.pdp.dto.ShowtimeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:56
 **/
@Component
public class ShowtimeDAO extends BaseDAO<Showtime, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected ShowtimeDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Showtime> rowMapper = (rs, rowNum) -> Showtime.builder()
            .id(rs.getInt("showtime_id"))
            .moveId(rs.getInt("movie_id"))
            .screenId(rs.getInt("screen_id"))
            .startTime(rs.getTimestamp("start_time").toLocalDateTime())
            .endTime(rs.getTimestamp("end_time").toLocalDateTime())
            .language(rs.getString("language"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .price(rs.getDouble("price"))
            .build();

    @Override
    public Integer save(Showtime entity) {
        var sql = "INSERT INTO Showtimes(movie_id, screen_id, start_time, end_time, language,price) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"showtime_id"});
            statement.setInt(1, entity.getMoveId());
            statement.setInt(2, entity.getScreenId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
            statement.setString(5, entity.getLanguage());
            statement.setDouble(6,entity.getPrice());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Showtime entity) {
        var sql = "UPDATE Showtimes SET movie_id=? AND screen_id=? AND start_time=? AND end_time=? AND language=? WHERE showtime_id=?;";
        jdbcTemplate.update(sql, entity.getMoveId(), entity.getScreenId(), entity.getStartTime(), entity.getEndTime(), entity.getLanguage(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE Showtimes SET is_active=false WHERE showtime_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Showtime findById(Integer uuid) {
        var sql = "SELECT * FROM Showtimes WHERE is_active AND showtime_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<Showtime> findAll() {
        var sql = "SELECT * FROM Showtimes WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<ShowtimeDTO> findByMovieId(Integer movieId) {
        var sql = "select * from Showtimes sh  inner join screens sc on sh.screen_id=sc.screen_id where sh.is_active and sh.movie_id=?;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> ShowtimeDTO.builder()
                .showtimeId(rs.getInt("showtime_id"))
                .movieId(rs.getInt("movie_id"))
                .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                .endTime(rs.getTimestamp("end_time").toLocalDateTime())
                .screenId(rs.getInt("screen_id"))
                .language(rs.getString("language"))
                .price(rs.getDouble("price"))
                .eventName(rs.getString("event_name"))
                .build(), movieId);
    }
}
