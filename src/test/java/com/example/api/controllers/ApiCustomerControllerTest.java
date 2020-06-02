package com.example.api.controllers;


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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiCustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    private static final String URL_BASE = "/customers/";
    private static final Long ID_CUSTOMER = 1L;

    /** TESTE CADASTRO DE UM NOVO CLIENTE*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alfredo");
        customer.setEmail("email@email.com");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.save(Mockito.any(Customer.class))).willReturn(customer);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Alfredo"))
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    /** TESTE CADASTRO DE UM NOVO CLIENTE COM NOME INVALIDO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomerInvalidName() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("");
        customer.setEmail("email@email.com");

        Gson gson = new Gson();

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /** TESTE CADASTRO DE UM NOVO CLIENTE COM FORMATO DO EMAIL INVALIDO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomerInvalidEmailFormat() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alfredo");
        customer.setEmail("email#emai");

        Gson gson = new Gson();

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must be a well-formed email address"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /** TESTE CADASTRO DE UM NOVO CLIENTE COM EMAIL VAZIO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateCustomerInvalidEmail() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alfredo");
        customer.setEmail("");

        Gson gson = new Gson();

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /***
     * A PARTIR DAQUI SÃO TESTES DE UPDATE
     */

    /** TESTE ATUALIZACAO DO CLIENTE DE ID 1L*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setId(ID_CUSTOMER);
        customer.setName("Julio");
        customer.setEmail("julio@email.com");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.findById(ID_CUSTOMER)).willReturn(Optional.of(new Customer()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BASE+"/{id}", ID_CUSTOMER)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Julio"))
                .andExpect(jsonPath("$.data.email").value("julio@email.com"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    /** TESTE UPDATE DE CLIENTE COM NOME INVALIDO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomerInvalidName() throws Exception {

        Customer customer = new Customer();
        customer.setId(ID_CUSTOMER);
        customer.setName("");
        customer.setEmail("julio@email.com");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.findById(ID_CUSTOMER)).willReturn(Optional.of(new Customer()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BASE+"/{id}", ID_CUSTOMER)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /** TESTE UPDATE DE CLIENTE COM FORMATO DE EMAIL INVALIDO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomerInvalidEmailFormat() throws Exception {

        Customer customer = new Customer();
        customer.setId(ID_CUSTOMER);
        customer.setName("Julio");
        customer.setEmail("julioemail");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.findById(ID_CUSTOMER)).willReturn(Optional.of(new Customer()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BASE+"/{id}", ID_CUSTOMER)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must be a well-formed email address"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /** TESTE UPDATE DE CLIENTE COM EMAIL VAZIO*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomerInvalidEmail() throws Exception {

        Customer customer = new Customer();
        customer.setId(ID_CUSTOMER);
        customer.setName("Julio");
        customer.setEmail("");

        Gson gson = new Gson();

        BDDMockito.given(this.customerService.findById(ID_CUSTOMER)).willReturn(Optional.of(new Customer()));

        mvc.perform(MockMvcRequestBuilders.put(URL_BASE+"/{id}", ID_CUSTOMER)
                .content(gson.toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /***
     * A PARTIR DAQUI SÃO TESTES DE DELETE
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCustomer() throws Exception {
        BDDMockito.given(this.customerService.findById(Mockito.anyLong())).willReturn(Optional.of(new Customer()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_CUSTOMER)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /** TESTE DELETE DE CLIENTE COM ID QUE NÃO EXISTE*/
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCustomerIdInvalid() throws Exception {
        BDDMockito.doNothing().when(customerService).delete(Mockito.any(Customer.class));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + 123456)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value("404 NOT_FOUND \"Customer not found\""))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /***
     * A PARTIR DAQUI SÃO TESTES DE GET
    */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCustomerFindAll() throws Exception {
        List all = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Alfredo");
        customer1.setEmail("alfredo@email.com");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Julio");
        customer2.setEmail("julio@email.com");

        all.addAll(Arrays.asList(customer1, customer2));

        BDDMockito.when(this.customerService.findAll(Pageable.unpaged())).thenReturn((Page<Customer>) all);

        List result = (List) this.customerService.findAll(Pageable.unpaged());

        BDDMockito.verify(customerService).findAll(Pageable.unpaged());
        assertEquals(2, result.size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCustomerFindById() throws Exception {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alfredo");
        customer.setEmail("alfredo@email.com");

        BDDMockito.when(this.customerService.findById(1L)).thenReturn(Optional.of(customer));

        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + ID_CUSTOMER)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Alfredo"))
                .andExpect(jsonPath("$.data.email").value("alfredo@email.com"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }
}