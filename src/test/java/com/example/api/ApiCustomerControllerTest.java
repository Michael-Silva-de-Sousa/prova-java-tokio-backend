package com.example.api;


import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiCustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    private static final String URL_BASE = "/customers";

    /** TESTE CADASTRO DE UM NOVO CLIENTE*/
    @Test
    public void testCreateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alfredo");
        customer.setEmail("email@email.com");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.create(Mockito.any(Customer.class))).willReturn(customer);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Alfredo"))
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }
}