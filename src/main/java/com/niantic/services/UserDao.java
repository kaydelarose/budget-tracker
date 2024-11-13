package com.niantic.services;

import com.niantic.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while (row.next()) {
            users.add(new User(
                    row.getInt("user_id"),
                    row.getString("user_name"),
                    row.getString("first_name"),
                    row.getString("last_name"),
                    row.getString("phone"),
                    row.getString("email")
            ));
        }

        return users;
    }

    public User getUserById(int userId) {
        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users
                WHERE user_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);

        if (row.next()) {
            return new User(
                    row.getInt("user_id"),
                    row.getString("user_name"),
                    row.getString("first_name"),
                    row.getString("last_name"),
                    row.getString("phone"),
                    row.getString("email")
            );
        }

        return null;
    }

    public User getUserByName(String name) {
        String sql = """
                SELECT user_id
                    , user_name
                    , first_name
                    , last_name
                    , phone
                    , email
                FROM users
                WHERE user_name = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, name);

        if (row.next()) {
            return new User(
                    row.getInt("user_id"),
                    row.getString("user_name"),
                    row.getString("first_name"),
                    row.getString("last_name"),
                    row.getString("phone"),
                    row.getString("email")
            );
        }

        return null;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (user_name, first_name, last_name, phone, email) VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql,
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail());
    }

    public void updateUser(User user) {
        String sql = """
                UPDATE users
                SET user_name = ?
                    , first_name = ?
                    , last_name = ?
                    , phone = ?
                    , email = ?
                WHERE user_id = ?;
                """;

        jdbcTemplate.update(sql,
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getUserId());
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?;";

        jdbcTemplate.update(sql, userId);
    }
}
