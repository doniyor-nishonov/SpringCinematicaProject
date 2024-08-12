package com.pdp.daos;

import com.pdp.domains.Ticket;
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
 * @since 04/August/2024  11:56
 **/
@Component
public class TicketDAO extends BaseDAO<Ticket, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected TicketDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Ticket> rowMapper = (rs, rowNum) -> Ticket.builder()
            .id(rs.getInt("ticket_id"))
            .userId(rs.getInt("ticket_id"))
            .showtimeId(rs.getInt("showtime_id"))
            .seatNumber(rs.getString("seat_number"))
            .purchaseTime(rs.getTimestamp("purchase_time").toLocalDateTime())
            .price(rs.getDouble("price"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(Ticket entity) {
        var sql = "INSERT INTO tickets(user_id, showtime_id, seat_number, price) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"ticket_id"});
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getShowtimeId());
            statement.setString(3, entity.getSeatNumber());
            statement.setDouble(4, entity.getPrice());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Ticket entity) {
        var sql = "UPDATE tickets SET user_id=? AND showtime_id=? AND seat_number=? AND price=? WHERE ticket_id=?;";
        jdbcTemplate.update(sql, entity.getUserId(), entity.getShowtimeId(), entity.getSeatNumber(), entity.getPrice(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE tickets SET is_active=false WHERE ticket_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Ticket findById(Integer uuid) {
        var sql = "SELECT * FROM tickets WHERE is_active AND ticket_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<Ticket> findAll() {
        var sql = "SELECT * FROM tickets WHERE is_active;";
        return jdbcTemplate.query(sql,rowMapper);
    }
}
