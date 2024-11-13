package com.niantic.controllers;

import com.niantic.models.Category;
import com.niantic.services.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CategoriesController {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        ArrayList<Category> categories = categoryDao.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryDao.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit")
    public String showEditCategoryForm(@RequestParam("id") int categoryId, Model model) {
        Category category = categoryDao.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/categories/edit")
    public String editCategory(@ModelAttribute("category") Category category) {
        categoryDao.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete")
    public String deleteCategory(@RequestParam("id") int categoryId) {
        categoryDao.deleteCategory(categoryId);
        return "redirect:/categories";
    }
}
