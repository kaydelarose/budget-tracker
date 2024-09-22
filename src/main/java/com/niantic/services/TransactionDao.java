package com.niantic.services;

import com.niantic.models.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDao() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/budget");
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssw0rd");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }


    public ArrayList<Transaction> getTransactionsByUser(int userId) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE user_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByMonth(int month) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE MONTH(transaction_date) = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, month);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByYear(int year) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE YEAR(transaction_date) = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, year);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByCategory(int categoryId) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE category_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, categoryId);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }


    public ArrayList<Transaction> getTransactionsByVendor(int vendorId) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE vendor_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, vendorId);

        while (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            transactions.add(new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            ));
        }

        return transactions;
    }

    public Transaction getTransactionById(int transactionId) {
        String sql = """
                SELECT transaction_id
                    , user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes
                FROM transactions
                WHERE transaction_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transactionId);

        if (row.next()) {
            Date date = row.getDate("transaction_date");
            LocalDate transactionDate = date != null ? date.toLocalDate() : null;
            return new Transaction(
                    row.getInt("transaction_id"),
                    row.getInt("user_id"),
                    row.getInt("category_id"),
                    row.getInt("vendor_id"),
                    transactionDate,
                    row.getBigDecimal("amount"),
                    row.getString("notes")
            );
        }

        return null;
    }

    public void addTransaction(Transaction transaction) {
        String sql = """
                INSERT INTO transactions
                    (user_id
                    , category_id
                    , vendor_id
                    , transaction_date
                    , amount
                    , notes)
                VALUES (?,?,?,?,?,?);
                """;

        jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getCategoryId(),
                transaction.getVendorId(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getNotes()
        );

    }

    public void updateTransaction(Transaction transaction) {
        String sql = """
                UPDATE transactions
                SET user_id = ?
                    , category_id = ?
                    , vendor_id = ?
                    , transaction_date = ?
                    , amount = ?
                    , notes = ?
                WHERE transaction_id = ?;
                """;

        jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getCategoryId(),
                transaction.getVendorId(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getNotes(),
                transaction.getTransactionId()
        );
    }

    public void deleteTransaction(int transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?;";

        jdbcTemplate.update(sql, transactionId);
    }

}
