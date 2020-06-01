package com.example.api.web.rest;

import java.util.List;

import com.example.api.resonse.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<Response<List<Customer>>> findAll() {
        Response<List<Customer>> response = new Response<>();
        List customers = service.findAll();
        response.setData(customers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Customer>> findById(@PathVariable Long id) {
        Response<Customer> response = new Response<Customer>();
        Customer customerResult = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        response.setData(customerResult);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<Customer>> create(@RequestBody @Valid Customer customer) {
        Response<Customer> response = new Response<Customer>();
        Customer customerResult = service.save(customer);
        response.setData(customerResult);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<Customer>> update(@PathVariable("id") Long id, @RequestBody @Valid Customer customer) {
        Response<Customer> response = new Response<Customer>();

        Customer customerResult = service.findById(id).map(
                customerDb -> {
                    customerDb.setName(customer.getName());
                    customerDb.setEmail(customer.getEmail());
                    service.save(customerDb);
                    return customerDb;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        response.setData(customerResult);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.findById(id).map(
                customer -> {
                    service.delete(customer);
                    return customer;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
