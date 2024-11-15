package com.niantic.controllers;

import com.niantic.models.*;
import com.niantic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ReportsController {

    private final TransactionDao transactionDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final VendorDao vendorDao;

    @Autowired
    public ReportsController(TransactionDao transactionDao, CategoryDao categoryDao, UserDao userDao, VendorDao vendorDao) {
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.vendorDao = vendorDao;
    }

    @GetMapping("/reports/category")
    public String getReportByCategory(Model model) {
        ArrayList<Category> categories = categoryDao.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "reports/category";
    }

    @GetMapping("/reports/user")
    public String getReportByUser(Model model) {
        ArrayList<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "reports/user";
    }

    @GetMapping("/reports/vendor")
    public String getReportByVendor(Model model) {
        ArrayList<Vendor> vendors = vendorDao.getAllVendors();
        model.addAttribute("vendors", vendors);
        model.addAttribute("vendor", new Vendor());
        return "reports/vendor";
    }

    @GetMapping("/reports/year")
    public String getReportByYear(Model model) {
        return "reports/year";
    }

    @GetMapping("reports/month")
    public String getReportByMonth() {
        return "reports/month";
    }

    @PostMapping("/reports/category")
    public String getReportsByCategory(Model model, @ModelAttribute("category") Category category) {
        int categoryId = category.getCategoryId();
        String categoryName = categoryDao.getCategoryById(categoryId).getCategoryName();
        ArrayList<Transaction> transactions = transactionDao.getTransactionsByCategory(categoryId);
        ArrayList<ReportLine> reportLines = new ArrayList<>();
        for (var transaction : transactions) {
            reportLines.add(new ReportLine(transaction, userDao, categoryDao, vendorDao));
        }
        model.addAttribute("reportlines", reportLines);
        model.addAttribute("reportType", "category");
        model.addAttribute("name", categoryName);
        return "reports/index";
    }

    @PostMapping("/reports/user")
    public String getReportsByUser(Model model, @ModelAttribute("user") User user) {
        int userId = user.getUserId();
        String userName = userDao.getUserById(userId).getUserName();
        ArrayList<Transaction> transactions = transactionDao.getTransactionsByUser(userId);
        ArrayList<ReportLine> reportLines = new ArrayList<>();
        for (var transaction : transactions) {
            reportLines.add(new ReportLine(transaction, userDao, categoryDao, vendorDao));
        }
        model.addAttribute("reportlines", reportLines);
        model.addAttribute("reportType", "user");
        model.addAttribute("name", userName);
        return "reports/index";
    }

    @PostMapping("/reports/vendor")
    public String getReportsByVendor(Model model, @ModelAttribute("vendor") Vendor vendor) {
        int vendorId = vendor.getVendorId();
        String vendorName = vendorDao.getVendorById(vendorId).getVendorName();
        ArrayList<Transaction> transactions = transactionDao.getTransactionsByVendor(vendorId);
        ArrayList<ReportLine> reportLines = new ArrayList<>();
        for (var transaction : transactions) {
            reportLines.add(new ReportLine(transaction, userDao, categoryDao, vendorDao));
        }
        model.addAttribute("reportlines", reportLines);
        model.addAttribute("reportType", "vendor");
        model.addAttribute("name", vendorName);
        return "reports/index";
    }

    @PostMapping("/reports/year")
    public String getReportByYear(Model model, @RequestParam(required = true) String year) {
        ArrayList<Transaction> transactions = transactionDao.getTransactionsByYear(Integer.parseInt(year));
        ArrayList<ReportLine> reportLines = new ArrayList<>();
        for (var transaction : transactions) {
            reportLines.add(new ReportLine(transaction, userDao, categoryDao, vendorDao));
        }
        model.addAttribute("reportlines", reportLines);
        model.addAttribute("reportType", "year");
        model.addAttribute("name", "" + year);
        return "reports/index";
    }

    @PostMapping("reports/month")
    public String getReportByMonth(Model model, @RequestParam(required = true) int month) {
        ArrayList<Transaction> transactions = transactionDao.getTransactionsByMonth(month);
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayList<ReportLine> reportLines = new ArrayList<>();
        for (var transaction : transactions) {
            reportLines.add(new ReportLine(transaction, userDao, categoryDao, vendorDao));
        }
        model.addAttribute("reportlines", reportLines);
        model.addAttribute("reportType", "month");
        model.addAttribute("name", months[month - 1]);
        return "reports/index";
    }
}
