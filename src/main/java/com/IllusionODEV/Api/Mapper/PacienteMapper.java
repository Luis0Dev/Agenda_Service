package com.IllusionODEV.Api.Mapper;

import com.IllusionODEV.Api.Request.PacienteRequest;
import com.IllusionODEV.Api.Response.PacienteCompletoResponse;
import com.IllusionODEV.Api.Response.PacienteResponse;
import com.IllusionODEV.Domain.Entity.Paciente;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final ModelMapper mapper;

    public Paciente toPaciente(PacienteRequest request) {
        return mapper.map(request, Paciente.class);
    }

    public PacienteResponse toPacienteResponse(Paciente paciente) {
        return mapper.map(paciente, PacienteResponse.class);
    }

    public PacienteCompletoResponse toPacienteCompletoResponse(Paciente paciente) {
        return mapper.map(paciente, PacienteCompletoResponse.class);
    }

    public List<PacienteResponse> toPacienteResponseList(List<Paciente> pacientes) {
        return pacientes.stream()
                .map(this::toPacienteResponse)
                .collect(Collectors.toList());
    }

}
