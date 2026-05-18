package com.nicole.raizes_do_nordeste.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicole.raizes_do_nordeste.application.dto.request.CadastroRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
@Transactional
class AutenticacaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("deve retornar 201 ao criar usuário")
    void cadastroBemSucedido() throws Exception {

        CadastroRequest cadastroRequest =
                new CadastroRequest(
                        "nomeTeste",
                        "email123@gmail.com",
                        "12345",
                        false
                );

        mockMvc.perform(post("/auth/register")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastroRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("deve retornar 400 ao cadastrar email duplicado")
    void cadastroEmailDuplicado() throws Exception {

        CadastroRequest cadastroRequest =
                new CadastroRequest(
                        "Nicole",
                        "email@gmail.com",
                        "123456",
                        true
                );

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cadastroRequest)));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastroRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deve retornar 400 ao enviar campos inválidos")
    void cadastroCamposInvalidos() throws Exception {

        CadastroRequest cadastroRequest =
                new CadastroRequest(
                        "",
                        "",
                        "",
                        null
                );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cadastroRequest)))
                .andExpect(status().isBadRequest());
    }


}