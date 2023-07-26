package com.example.demo.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    @ResponseBody
    public List<Customer> findCustomers(){
        return customerService.getAllCustomers();
    }

//    @RequestMapping(value = "/customers",method = RequestMethod.GET)  아래 getmapping과 동일한 기능
    @GetMapping("/customers")
    public String viewCustomersPage(Model model){
        var allCustomers = customerService.getAllCustomers();
        model.addAttribute("serverTime",LocalDateTime.now());
        model.addAttribute("customers",allCustomers);
        return "views/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model){
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()){
            model.addAttribute("customer",maybeCustomer.get());
            return "views/customer-details";
        }else{
            return "views/404";
        }

    }

    @GetMapping("/customers/new")
    public String viewNesCustomerPage(){
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest){

        customerService.createCustomer(createCustomerRequest.getEmail() , createCustomerRequest.getName());
        return "redirect:/customers";

    }
}
