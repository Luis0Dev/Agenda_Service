package com.IllusionODEV.Api.Controller;

import com.IllusionODEV.Api.Request.PacienteRequest;
import com.IllusionODEV.Domain.Entity.Paciente;
import com.IllusionODEV.Domain.Repository.PacienteRepository;
import com.IllusionODEV.Exception.BusinessException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteRepository pacienteRepository;

    @BeforeEach
    void up(){
        Paciente paciente = new Paciente();
        paciente.setNome("Mario");
        paciente.setSobrenome("Ferrari");
        paciente.setCpf("123456");
        paciente.setEmail("mario@gmail.com");
        pacienteRepository.save(paciente);
    }

    @AfterEach
    void down(){
        pacienteRepository.deleteAll();
    }

    @Test
    @DisplayName("Listar todos pacientes")
    void listaPacientes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Listar paciente por id")
    void listaPacientePorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/paciente/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPaciente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("joao@mail.com")
                .nome("joao")
                .sobrenome("silva")
                .cpf("234")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com sucesso")
    void salvarPacienteComCpfExistente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("pedro@mail.com")
                .nome("pedro")
                .sobrenome("silva")
                .cpf("123")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andDo(MockMvcResultHandlers.print());
    }
}
