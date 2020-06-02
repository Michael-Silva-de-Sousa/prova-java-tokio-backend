package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.CustomerAddressDTO;
import com.example.api.dto.CustomerAddressPerZipCodeDTO;
import com.example.api.resonse.Response;
import com.example.api.service.AddressApiViaCEPService;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
@Api("Api Address")
public class AddressController {

    @Autowired
    public AddressService addressService;

    @Autowired
    public AddressApiViaCEPService addressApiViaCEPService;

    @Autowired
    public CustomerService customerService;

    @PostMapping
    @ApiOperation("Permite incluir endereços associando-os ao Cliente informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereço salvo com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
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
    @ApiOperation("Permite incluir endereços ao Cliente informado apenas ao informar o CEP.\n" +
                    "O CEP informado é consultado via consumo do serviço: https://viacep.com.br/")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereços salvos com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public ResponseEntity<Response<Customer>> savePerZipCode(@RequestBody CustomerAddressPerZipCodeDTO customerAddressPerZipCodeDTO) {
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
