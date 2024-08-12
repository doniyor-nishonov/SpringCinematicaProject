package com.pdp.daos;

import com.pdp.domains.AuthUser;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  20:09
 **/
@Component
public class UserDAO extends BaseDAO<AuthUser, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */
    protected UserDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<AuthUser> rowMapper = (rs, numRow) -> AuthUser.builder()
            .id(rs.getInt("user_id"))
            .name(rs.getString("name"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .phoneNumber(rs.getString("phone_number"))
            .role(rs.getString("role"))
            .imageId(rs.getInt("image_id"))
            .status(rs.getString("status"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Integer save(AuthUser entity) {
        return jdbcTemplate.queryForObject("SELECT save_user(?,?,?,?,?)",
                Integer.class, entity.getName(), entity.getUsername(), entity.getPassword(), entity.getPhoneNumber(), entity.getImageId());
    }

    @Override
    public void update(AuthUser entity) {
        var sql = "UPDATE users SET name=?, username=?, password=?, phone_number=?, role=?, status=? , image_id=? WHERE user_id=?";
        jdbcTemplate.update(sql,
                entity.getName(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getRole(),
                entity.getStatus(),
                entity.getImageId(),
                entity.getId());
    }


    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE users SET is_active=false WHERE user_id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public AuthUser findById(Integer uuid) {
        var sql = "SELECT * FROM users WHERE is_active AND user_id=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, uuid);
    }

    @Override
    public List<AuthUser> findAll() {
        var sql = "SELECT * FROM users WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public AuthUser login(@NonNull String username, @NonNull String password) {
        var sql = "SELECT * FROM users WHERE is_active AND username=? AND password=?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username, password);
    }

    public Optional<AuthUser> findByUsername(String username) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where is_active and username=? and status='ACTIVE';", rowMapper, username));
    }

    public List<AuthUser> findAllWithoutAdmin() {
        var sql = "select * from users where is_active=true and role='USER'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public AuthUser findByPhoneNumber(String phoneNumber) {
        try {
            return jdbcTemplate.queryForObject("select * from users where is_active and status='ACTIVE' and phone_number=?;", rowMapper, phoneNumber);
        } catch (Exception e) {
            return null;
        }
    }

    public void updatePassword(Integer userId, String password) {
        jdbcTemplate.update("update users set password = ? where user_id=?;", password, userId);
    }
}
