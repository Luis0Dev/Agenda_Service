package com.IllusionODEV.Api.Controller;

import com.IllusionODEV.Api.Mapper.PacienteMapper;
import com.IllusionODEV.Api.Request.PacienteRequest;
import com.IllusionODEV.Api.Response.PacienteCompletoResponse;
import com.IllusionODEV.Api.Response.PacienteResponse;
import com.IllusionODEV.Domain.Entity.Paciente;
import com.IllusionODEV.Domain.Service.PacienteService;

import lombok.RequiredArgsConstructor;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;
    private final Logger log = LoggerFactory.getLogger(PacienteController.class);

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar(@Valid  @RequestBody PacienteRequest request){

        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvar = service.salvar(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvar);
        return  ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos(){

        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

        List<PacienteResponse> pacienteResponses = service.listarTodos()
                .stream()
                .map(mapper::toPacienteResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponses);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<PacienteCompletoResponse> buscarPorId(@PathVariable Long id){

        log.info("realizando busca por paciente id: {}", id);
        return service.buscarPorId(id)
                .map(mapper::toPacienteCompletoResponse)
                .map(pacienteCompletoResponse -> ResponseEntity.status(HttpStatus.OK).body(pacienteCompletoResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<PacienteResponse> alterar(@PathVariable Long id, @RequestBody PacienteRequest request){

        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.alterar(id, paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return  ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
