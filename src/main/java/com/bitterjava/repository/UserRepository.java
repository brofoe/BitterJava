package com.bitterjava.repository;

import com.bitterjava.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNumber) ->
            new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password_hash"));

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT id, username, password_hash FROM users WHERE username = ?",
                USER_ROW_MAPPER,
                username);
        return users.stream().findFirst();
    }

    public User insert(String username, String passwordHash) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password_hash) VALUES (?, ?)",
                    new String[]{"ID"});
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("The database did not return the new user's ID.");
        }
        return new User(generatedId.longValue(), username, passwordHash);
    }
}
