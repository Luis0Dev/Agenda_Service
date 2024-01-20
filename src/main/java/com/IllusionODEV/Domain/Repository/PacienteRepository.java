package com.IllusionODEV.Domain.Repository;

import com.IllusionODEV.Domain.Entity.Paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional <Paciente> findByCpf(String cpd);
}
