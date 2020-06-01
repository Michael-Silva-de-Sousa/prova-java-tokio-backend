package com.example.api.repository;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

}
