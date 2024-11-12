package com.niantic.services;

import com.niantic.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = """
                SELECT category_id, category_name, description
                FROM categories;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while (row.next()) {
            categories.add(mapRowToCategory(row));
        }

        return categories;
    }

    public Category getCategoryById(int categoryId) {
        String sql = """
                SELECT category_id, category_name, description
                FROM categories
                WHERE category_id = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, categoryId);

        return row.next() ? mapRowToCategory(row) : null;
    }

    public Category getCategoryByName(String name) {
        String sql = """
                SELECT category_id, category_name, description
                FROM categories
                WHERE category_name = ?;
                """;

        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, name);

        return row.next() ? mapRowToCategory(row) : null;
    }

    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (category_name, description) VALUES (?, ?);";
        jdbcTemplate.update(sql, category.getCategoryName(), category.getDescription());
    }

    public void updateCategory(Category category) {
        String sql = """
                UPDATE categories
                SET category_name = ?, description = ?
                WHERE category_id = ?;
                """;

        jdbcTemplate.update(sql, category.getCategoryName(), category.getDescription(), category.getCategoryId());
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?;";
        jdbcTemplate.update(sql, categoryId);
    }

    private Category mapRowToCategory(SqlRowSet row) {
        return new Category(
                row.getInt("category_id"),
                row.getString("category_name"),
                row.getString("description")
        );
    }
}
