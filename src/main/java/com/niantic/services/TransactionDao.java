package com.niantic.services;

import com.niantic.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = """
                SELECT transaction_id, user_id, category_id, vendor_id, transaction_date, amount, notes
                FROM transactions;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
        while (row.next()) {
            transactions.add(mapRowToTransaction(row));
        }
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByUser(int userId) {
        return queryTransactions("WHERE user_id = ?", userId);
    }

    public ArrayList<Transaction> getTransactionsByMonth(int month) {
        return queryTransactions("WHERE MONTH(transaction_date) = ?", month);
    }

    public ArrayList<Transaction> getTransactionsByYear(int year) {
        return queryTransactions("WHERE YEAR(transaction_date) = ?", year);
    }

    public ArrayList<Transaction> getTransactionsByCategory(int categoryId) {
        return queryTransactions("WHERE category_id = ?", categoryId);
    }

    public ArrayList<Transaction> getTransactionsByVendor(int vendorId) {
        return queryTransactions("WHERE vendor_id = ?", vendorId);
    }

    public Transaction getTransactionById(int transactionId) {
        String sql = """
                SELECT transaction_id, user_id, category_id, vendor_id, transaction_date, amount, notes
                FROM transactions
                WHERE transaction_id = ?;
                """;
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transactionId);
        return row.next() ? mapRowToTransaction(row) : null;
    }

    public void addTransaction(Transaction transaction) {
        String sql = """
                INSERT INTO transactions (user_id, category_id, vendor_id, transaction_date, amount, notes)
                VALUES (?, ?, ?, ?, ?, ?);
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
                SET user_id = ?, category_id = ?, vendor_id = ?, transaction_date = ?, amount = ?, notes = ?
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

    private Transaction mapRowToTransaction(SqlRowSet row) {
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

    private ArrayList<Transaction> queryTransactions(String whereClause, Object param) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = """
                SELECT transaction_id, user_id, category_id, vendor_id, transaction_date, amount, notes
                FROM transactions
                """ + whereClause;
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, param);
        while (row.next()) {
            transactions.add(mapRowToTransaction(row));
        }
        return transactions;
    }
}
