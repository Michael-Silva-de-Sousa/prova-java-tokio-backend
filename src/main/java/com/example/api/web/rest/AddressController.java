package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.CustomerAddressDTO;
import com.example.api.dto.CustomerAddressPerZipCodeDTO;
import com.example.api.resonse.Response;
import com.example.api.service.AddressApiViaCEPService;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
public class AddressController {

    private static final RestTemplate Endereco = null;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressApiViaCEPService addressApiViaCEPService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response<Customer>> save(@RequestBody CustomerAddressDTO addressDTO) {
        Response<Customer> response = new Response<Customer>();

        Customer customer = customerService.findById(addressDTO.getIdCustomer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        addressDTO.getAddresses().forEach(address -> address.setCustomer(customer));
        addressService.save(addressDTO.getAddresses());

        response.setData(customerService.findById(addressDTO.getIdCustomer()).get());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/zipcode")
    public ResponseEntity<Response<Customer>> savePerZipCode(@RequestBody @Valid CustomerAddressPerZipCodeDTO customerAddressPerZipCodeDTO) {
        Response<Customer> response = new Response<Customer>();

        Customer customer = customerService.findById(customerAddressPerZipCodeDTO.getIdCustomer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        List<Address> addresses = customerAddressPerZipCodeDTO.getZipCodes().stream().map(zipCode -> {
                    Address address = addressApiViaCEPService.findAddressPerZipCode(zipCode);
                    address.setCustomer(customer);
                    return address;
            }).collect(Collectors.toList());

        addressService.save(addresses);

        response.setData(customerService.findById(customerAddressPerZipCodeDTO.getIdCustomer()).get());
        return ResponseEntity.ok(response);
    }

}
