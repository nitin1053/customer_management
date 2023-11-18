package com.example.emp_system_ip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.emp_system_ip.entity.Employee;
import com.example.emp_system_ip.service.EmpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private EmpService empService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        // Check if the user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            // Redirect to login page if not logged in
            return "redirect:/login";
        }

        List<Employee> list = empService.getAllEmp();
        model.addAttribute("empList", list);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login_id, @RequestParam String password, HttpSession session) {
        // Validate login credentials
        if ("test@sunbasedata.com".equals(login_id) && "Test@123".equals(password)) {
            session.setAttribute("loggedInUser", login_id);
            return "redirect:/"; // Redirect to the index page
        } else {
            session.setAttribute("msg", "Invalid credentials. Please try again.");
            return "redirect:/login";
        }
    }

    @GetMapping("/loadEmpSave")
    public String loadEmpSave() {
        return "emp_save";
    }

    @GetMapping("/EditEmp/{id}")
    public String EditEmp(@PathVariable int id, Model model) {
        Employee emp = empService.getEmpById(id);
        model.addAttribute("emp", emp);
        return "edit_emp";
    }

    @PostMapping("/saveEmp")
    public String saveEmp(@ModelAttribute Employee emp, HttpSession session) {
        Employee newEmp = empService.saveEmp(emp);

        if (newEmp != null) {
            session.setAttribute("msg", "Register successfully");
        } else {
            session.setAttribute("msg", "Something wrong on the server");
        }

        return "redirect:/loadEmpSave";
    }

    @PostMapping("/updateEmpDtls")
    public String updateEmp(@ModelAttribute Employee emp, HttpSession session) {
        Employee updateEmp = empService.saveEmp(emp);

        if (updateEmp != null) {
            session.setAttribute("msg", "Update successfully");
        } else {
            session.setAttribute("msg", "Something wrong on the server");
        }

        return "redirect:/";
    }

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmp(@PathVariable int id, HttpSession session) {
        boolean f = empService.deleteEmp(id);
        if (f) {
            session.setAttribute("msg", "Delete successfully");
        } else {
            session.setAttribute("msg", "Something wrong on the server");
        }
        return "redirect:/";
    }
}
