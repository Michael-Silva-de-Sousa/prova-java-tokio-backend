package com.example.api.web.rest;

import java.util.List;

import com.example.api.resonse.Response;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@Api("Api Customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    @ApiOperation("Permite buscar todos os Clientes cadastrados na base.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
    })
    public ResponseEntity<Response<Page<Customer>>> findAll(Pageable pageable) {
        Response<Page<Customer>> response = new Response<>();
        Page customers = service.findAll(pageable);
        response.setData(customers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("Permite buscar por ID os Clientes cadastrados na base.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public ResponseEntity<Response<Customer>> findById(@PathVariable @ApiParam("Id do Cliente") Long id) {
        Response<Customer> response = new Response<Customer>();
        Customer customerResult = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        response.setData(customerResult);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Permite criar novos Clientes.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente criado com sucesso"),
    })
    public ResponseEntity<Response<Customer>> create(@RequestBody @Valid Customer customer) {
        Response<Customer> response = new Response<Customer>();
        Customer customerResult = service.save(customer);
        response.setData(customerResult);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    @ApiOperation("Permite atualizar o Cliente por ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizacao realizada com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public ResponseEntity<Response<Customer>> update(@PathVariable("id") @ApiParam("Id do Cliente") Long id, @RequestBody @Valid Customer customer) {
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
    @ApiOperation("Permite deletar Cliente por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public void delete(@PathVariable @ApiParam("Id do Cliente") Long id){
        service.findById(id).map(
                customer -> {
                    service.delete(customer);
                    return customer;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
