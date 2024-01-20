package com.IllusionODEV.Domain.Repository;

import java.time.LocalDateTime;

public interface PacienteAgendaRepository {

    String getNome();
    String getCpf();
    LocalDateTime getHorario();
    String getDescricao();

}
