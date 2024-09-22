package com.niantic.controllers;

import com.niantic.models.*;
import com.niantic.services.CategoryDao;
import com.niantic.services.TransactionDao;
import com.niantic.services.UserDao;
import com.niantic.services.VendorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


@Controller
public class TransactionsController {
    TransactionDao transactionDao = new TransactionDao();
    CategoryDao categoryDao = new CategoryDao();
    UserDao userDao = new UserDao();
    VendorDao vendorDao = new VendorDao();


    @GetMapping("/transactions")
    public String getTransactions(Model model) {

        ArrayList<Transaction> transactions;

        transactions = transactionDao.getAllTransactions();

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
        ReportLine reportLine = new ReportLine(transaction);

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

