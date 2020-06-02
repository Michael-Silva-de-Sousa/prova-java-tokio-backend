package com.example.api.controllers;

import com.example.api.dto.CredentialDTO;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String URL_BASE = "/users/auth";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MockMvc mvc;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder(){
            return new RestTemplateBuilder().basicAuthentication("admin", "1234");
        }
    }

    @Test
    public void authenticateUserCredentialInvalid() throws Exception {
        CredentialDTO credentialDTO = new CredentialDTO("jose", "1234567890");

        Gson gson = new Gson();

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(credentialDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticateUserCredentialValid() throws Exception {
        CredentialDTO credentialDTO = new CredentialDTO("admin", "1234");

        Gson gson = new Gson();

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(gson.toJson(credentialDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
