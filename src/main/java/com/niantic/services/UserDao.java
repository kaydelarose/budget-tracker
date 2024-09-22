package com.niantic.services;

import com.niantic.models.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
public class UserDao
{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/budget");
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssw0rd");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<User> getAllUsers()
    {

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


        while(row.next())
        {

            int userId = row.getInt("user_id");
            String userName = row.getString("user_name");
            String firstName = row.getString("first_name");
            String lastName = row.getString("last_name");
            String phone = row.getString("phone");
            String email = row.getString("email");

            User user = new User(userId, userName, firstName, lastName, phone, email);

            users.add(user);

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

        var row = jdbcTemplate.queryForRowSet(sql, userId);

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
                SELECT user_name
                    , first_name
                    , last_name
                FROM users
                WHERE name = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, name);

        if(row.next()) {
            String userName = row.getString("user_name");
            String firstName = row.getString("first_name");
            String lastName = row.getString("last_name");

            var usersByName = new User();
            usersByName.setUserName(userName);
            usersByName.setFirstName(firstName);
            usersByName.setLastName(lastName);

            return usersByName;

        }

        return null;

    }

    public void addUser(User user)
    {

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
                WHERE user_id = ?    
                """;

        jdbcTemplate.update(sql
                , user.getUserName()
                , user.getFirstName()
                , user.getLastName()
                , user.getPhone()
                , user.getEmail()
                , user.getUserId());

    }

    public void deleteUser(int userId)
    {
        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        jdbcTemplate.update(sql, userId);
    }




}
