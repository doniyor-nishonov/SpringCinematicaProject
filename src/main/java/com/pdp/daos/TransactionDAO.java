package com.pdp.daos;

import com.pdp.domains.Transaction;
import com.pdp.dto.TransactionDTO;
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
 * @since 04/August/2024  11:57
 **/
@Component
public class TransactionDAO extends BaseDAO<Transaction, Integer> {
    /**
     * Constructs a new {@code BaseDAO} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} to be used for database operations.
     */

    protected TransactionDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private final RowMapper<Transaction> rowMapper = (rs, rowNum) -> Transaction.builder()
            .id(rs.getInt("transaction_id"))
            .userId(rs.getInt("user_id"))
            .totalAmount(rs.getDouble("total_amount"))
            .transactionDate(rs.getTimestamp("transaction_date").toLocalDateTime())
            .paymentMethod(rs.getString("payment_method"))
            .isActive(rs.getBoolean("is_active"))
            .createdDate(rs.getTimestamp("created_at").toLocalDateTime())
            .cardHolderName(rs.getString("card_holder_name"))
            .cardNumber(rs.getString("card_number"))
            .expiryDate(rs.getString("expiry_date"))
            .cvv(rs.getString("cvv"))
            .ticketId(rs.getInt("ticket_id"))
            .build();

    @Override
    public Integer save(Transaction entity) {
        var sql = "INSERT INTO transactions(user_id,total_amount,payment_method,card_holder_name,card_number,expiry_date,cvv,ticket_id)" +
                " VALUES (?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"transaction_id"});
            statement.setInt(1, entity.getUserId());
            statement.setDouble(2, entity.getTotalAmount());
            statement.setString(3, entity.getPaymentMethod());
            statement.setString(4, entity.getCardHolderName());
            statement.setString(5, entity.getCardNumber());
            statement.setString(6, entity.getExpiryDate());
            statement.setString(7, entity.getCvv());
            statement.setInt(8, entity.getTicketId());
            return statement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void update(Transaction entity) {
        var sql = "UPDATE transactions SET user_id=? AND total_amount=? AND payment_method=? WHERE id=?;";
        jdbcTemplate.update(sql, entity.getUserId(), entity.getTotalAmount(), entity.getPaymentMethod(), entity.getId());
    }

    @Override
    public void delete(Integer uuid) {
        var sql = "UPDATE transactions SET is_active=false WHERE id=?;";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public Transaction findById(Integer uuid) {
        var sql = "SELECT * FROM transactions WHERE is_active AND id=?;";
        jdbcTemplate.queryForObject(sql, rowMapper, uuid);
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        var sql = "SELECT * FROM transactions WHERE is_active;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TransactionDTO> findAllByUserId(Integer id) {
        var sql = """
select ts.payment_method,
       ts.total_amount,
       ts.transaction_date,
       t.seat_number,
       m.title
from transactions ts
         inner join tickets t on ts.ticket_id = t.ticket_id
         inner join showtimes s on t.showtime_id = s.showtime_id
         inner join movies m on s.movie_id = m.movie_id where ts.user_id=?;
""";
        return jdbcTemplate.query(sql, (rs, rowNum) -> TransactionDTO.builder()
                .movieName(rs.getString("title"))
                .transactionDate(rs.getTimestamp("transaction_date").toLocalDateTime().toLocalDate())
                .seats(rs.getString("seat_number"))
                .paymentMethod(rs.getString("payment_method"))
                .totalAmount(rs.getDouble("total_amount"))
                .build(), id);
    }
}
