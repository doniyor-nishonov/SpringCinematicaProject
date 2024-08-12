package com.pdp.daos;

import com.pdp.domains.HallSeat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 08/August/2024  11:54
 **/
@Component
public class HallSeatDAO extends BaseDAO<HallSeat, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected HallSeatDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<HallSeat> rowMapper = (rs, rowNum) -> HallSeat.builder()
            .id(rs.getInt("id"))
            .showtimeId(rs.getInt("showtime_id"))
            .seatNumber(rs.getInt("seat_number"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(HallSeat entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var sql = "insert into hall_seat (showtime_id, seat_number) values (?,?);";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            statement.setInt(1, entity.getShowtimeId());
            statement.setInt(2, entity.getSeatNumber());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(HallSeat entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public HallSeat findById(Integer integer) {
        return jdbcTemplate.queryForObject("select * from hall_seat where is_active and id=?;", rowMapper, integer);
    }

    @Override
    public List<HallSeat> findAll() {
        return jdbcTemplate.query("select * from hall_seat where is_active;", rowMapper);
    }

    public List<Integer> findSeatNumberByShowtimeId(Integer showtimeId) {
        String sql = "SELECT seat_number FROM hall_seat WHERE is_active = true AND showtime_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("seat_number"), showtimeId);
    }

}
