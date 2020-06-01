package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

}
