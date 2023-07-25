package com.example.demo.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


//    @RequestMapping(value = "/customers",method = RequestMethod.GET)  아래 getmapping과 동일한 기능
    @GetMapping("/customers")
    public String findCustomers(Model model){
        var allCustomers = customerService.getAllCustomers();
        model.addAttribute("serverTime",LocalDateTime.now());
        model.addAttribute("customers",allCustomers);
        return "views/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(UUID customerId,Model model){
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()){
            model.addAttribute("customer",maybeCustomer.get());
            
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
