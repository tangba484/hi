package com.example.demo.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            customerRepository.insert(customer);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(String email,String name) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.insert(customer);
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }
}
