package com.IllusionODEV.Domain.Service;

import com.IllusionODEV.Domain.Entity.Agenda;
import com.IllusionODEV.Domain.Entity.Paciente;
import com.IllusionODEV.Domain.Repository.AgendaRepository;

import com.IllusionODEV.Exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    AgendaService pacienteService;

    @Mock
    AgendaRepository pacienteRepository;

    @Captor
    ArgumentCaptor<Agenda> agendaArgumentCaptor;

    @Test
    @DisplayName("Deve salvar agendamento com sucesso")
    void salvarComSucesso() {

        // arrange
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(agenda.getPaciente().getId())).thenReturn(Optional.of(paciente));
        Mockito.when(pacienteRepository.findByHorario(now)).thenReturn(Optional.empty());

        // action
        pacienteService.salvar(agenda);

        // assertions
        Mockito.verify(pacienteService).buscarPorId(ArgumentMatchers.any());
        Mockito.verify(pacienteService).buscarPorId(agenda.getPaciente().getId());
        Mockito.verify(pacienteRepository).findByHorario(now);

        Mockito.verify(pacienteRepository).save(agendaArgumentCaptor.capture());
        Agenda agendaSalva = agendaArgumentCaptor.getValue();

        Assertions.assertThat(agendaSalva.getPaciente()).isNotNull();
        Assertions.assertThat(agendaSalva.getCriacao()).isNotNull();
    }

    @Test
    @DisplayName("Não deve salvar agendamento sem paciente")
    void salvarErroPacienteNaoEncontrado() {

        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(ArgumentMatchers.any())).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            pacienteService.salvar(agenda);
        });

        Assertions.assertThat(businessException.getMessage()).isEqualTo("Paciente não encontrado");
    }
}
