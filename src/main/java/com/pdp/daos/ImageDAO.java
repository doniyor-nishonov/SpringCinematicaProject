package com.pdp.daos;

import com.pdp.domains.Image;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 05/August/2024  09:42
 **/
@Component
public class ImageDAO extends BaseDAO<Image, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected ImageDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Image> rowMapper = (rs, rowNum) -> Image.builder()
            .id(rs.getInt("image_id"))
            .name(rs.getString("name"))
            .generatedName(rs.getString("generated_name"))
            .mimeType(rs.getString("mime_type"))
            .extension(rs.getString("extension"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(Image entity) {
        var sql = "insert into images (name, generated_name, mime_type, extension) values (?,?,?,?);";
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"image_id"});
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getGeneratedName());
            statement.setString(3, entity.getMimeType());
            statement.setString(4, entity.getExtension());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Image entity) {

    }

    @Override
    public void delete(Integer uuid) {

    }
    @Override
    public Image findById(Integer uuid) {
        try {
            return jdbcTemplate.queryForObject("select * from images where is_active = true and image_id = ?;", rowMapper, uuid);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no result is found
        }
    }


    @Override
    public List<Image> findAll() {
        return jdbcTemplate.query("select * from images where is_active;",rowMapper);
    }
}
