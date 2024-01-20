package com.IllusionODEV.Domain.Service;

import com.IllusionODEV.Domain.Entity.Agenda;
import com.IllusionODEV.Domain.Entity.Paciente;
import com.IllusionODEV.Domain.Repository.AgendaRepository;
import com.IllusionODEV.Exception.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;
    private final PacienteService pacienteService;

    public List<Agenda> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Agenda salvar(Agenda agenda) {
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(agenda.getPaciente().getId());

        if (pacienteOptional.isEmpty()) {
            throw new BusinessException("Paciente não encontrado");
        }

        Optional<Agenda> horarioOptional = repository.findByHorario(agenda.getHorario());

        if (horarioOptional.isPresent()) {
            throw new BusinessException("Já existe agendamento para este horário");
        }

        agenda.setPaciente(pacienteOptional.get());
        agenda.setCriacao(LocalDateTime.now());
        return repository.save(agenda);
    }
}
