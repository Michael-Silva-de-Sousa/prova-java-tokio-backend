package com.example.api.service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Customer save(Customer customer){
		return repository.save(customer);
	}

    public void delete(Customer customer) {
			repository.delete(customer);
    }
}
