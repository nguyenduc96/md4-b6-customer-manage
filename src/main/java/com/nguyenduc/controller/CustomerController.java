package com.nguyenduc.controller;

import com.nguyenduc.model.Customer;
import com.nguyenduc.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView  = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        ModelAndView modelAndView  = new ModelAndView("edit");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable Long id) {
        ModelAndView modelAndView  = new ModelAndView("delete");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:/home";
    }


    @PostMapping("/save")
    public ModelAndView create(@ModelAttribute Customer customer) {
        ModelAndView modelAndView;
        if (customer.getId() == null) {
            modelAndView = new ModelAndView("create");
            modelAndView.addObject("message", "Created successfully!!!");
        } else {
            modelAndView = new ModelAndView("edit");
            modelAndView.addObject("message", "Updated successfully!!!");
        }
        customerService.save(customer);
        return modelAndView;
    }
}
