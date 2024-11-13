package com.niantic.controllers;

import com.niantic.models.*;
import com.niantic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TransactionsController {

    private final TransactionDao transactionDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final VendorDao vendorDao;

    @Autowired
    public TransactionsController(TransactionDao transactionDao, CategoryDao categoryDao, UserDao userDao, VendorDao vendorDao) {
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.vendorDao = vendorDao;
    }

    @GetMapping("/transactions")
    public String getTransactions(Model model) {
        ArrayList<Transaction> transactions = transactionDao.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions/index";
    }

    @GetMapping("/transactions/add")
    public String addTransaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("action", "add");

        ArrayList<Category> categories = categoryDao.getAllCategories();
        model.addAttribute("categories", categories);

        ArrayList<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);

        ArrayList<Vendor> vendors = vendorDao.getAllVendors();
        model.addAttribute("vendors", vendors);

        return "transactions/add_edit";
    }

    @PostMapping("/transactions/add")
    public String addTransaction(Model model, @ModelAttribute("transaction") Transaction transaction) {
        transactionDao.addTransaction(transaction);
        model.addAttribute("transaction", transaction);
        return "transactions/add_success";
    }

    @GetMapping("/transactions/{id}/edit")
    public String editTransaction(Model model, @PathVariable int id) {
        Transaction transaction = transactionDao.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("action", "edit");

        ArrayList<Category> categories = categoryDao.getAllCategories();
        model.addAttribute("categories", categories);

        ArrayList<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);

        ArrayList<Vendor> vendors = vendorDao.getAllVendors();
        model.addAttribute("vendors", vendors);

        return "transactions/add_edit";
    }

    @PostMapping("/transactions/{id}/edit")
    public String editTransaction(Model model, @ModelAttribute("transaction") Transaction transaction, @PathVariable int id) {
        transaction.setTransactionId(id);
        transactionDao.updateTransaction(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/transactions/{id}/delete")
    public String deleteTransaction(Model model, @PathVariable int id) {
        Transaction transaction = transactionDao.getTransactionById(id);
        ReportLine reportLine = new ReportLine(transaction, userDao, categoryDao, vendorDao);

        model.addAttribute("transaction", transaction);
        model.addAttribute("reportLine", reportLine);

        return "transactions/delete";
    }

    @PostMapping("/transactions/{id}/delete")
    public String deleteTransaction(@PathVariable int id) {
        transactionDao.deleteTransaction(id);
        return "redirect:/transactions";
    }
}
