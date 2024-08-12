package com.pdp.daos;

import com.pdp.domains.Screen;
import com.pdp.dto.ScreenDTO;
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
public class ScreenDAO extends BaseDAO<Screen, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected ScreenDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Screen> rowMapper = (rs, rowNum) -> Screen.builder()
            .id(rs.getInt("screen_id"))
            .cinemaID(rs.getInt("cinema_id"))
            .seatingCapacity(rs.getInt("seating_capacity"))
            .soundSystem(rs.getString("sound_system"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .imageId(rs.getInt("image_id"))
            .name(rs.getString("event_name"))
            .build();

    @Override
    public Integer save(Screen entity) {
        var sql = "INSERT INTO screens(cinema_id, seating_capacity, sound_system,image_id,event_name) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"screen_id"});
            statement.setInt(1, entity.getCinemaID());
            statement.setInt(2, entity.getSeatingCapacity());
            statement.setString(3, entity.getSoundSystem());
            statement.setInt(4, entity.getImageId());
            statement.setString(5, entity.getName());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Screen entity) {
        var sql = "UPDATE screens SET cinema_id=? AND  seating_capacity=? AND sound_system=? WHERE screen_id=?;";
        jdbcTemplate.update(sql, entity.getCinemaID(), entity.getSeatingCapacity(), entity.getSoundSystem(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE screens SET is_active=false WHERE screen_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Screen findById(Integer uuid) {
        var sql = "SELECT * FROM screens WHERE is_active AND screen_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<Screen> findAll() {
        var sql = "SELECT * FROM screens WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<ScreenDTO> findAllWithImages() {
        var sql = "select s.* ,i.* from screens s inner join images i on s.image_id = i.image_id where s.is_active and i.is_active;";
        RowMapper<ScreenDTO> dtoRowMapper = (rs, rowNum) -> ScreenDTO.builder()
                .screenId(rs.getInt("screen_id"))
                .name(rs.getString("event_name"))
                .seatingCapacity(rs.getInt("seating_capacity"))
                .soundSystem(rs.getString("sound_system"))
                .imageName(rs.getString("name"))
                .imageExtension(rs.getString("extension"))
                .build();
        return jdbcTemplate.query(sql, dtoRowMapper);
    }
}
