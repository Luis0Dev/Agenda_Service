package com.IllusionODEV.Domain.Service;

import com.IllusionODEV.Domain.Entity.Paciente;
import com.IllusionODEV.Domain.Repository.PacienteRepository;

import com.IllusionODEV.Exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {


    private final PacienteRepository repository;

    public Paciente salvar(Paciente paciente){

        boolean existCpf = false;

        // TODO: pra validar se o CPF existe.
       Optional<Paciente> pacienteCpf = repository.findByCpf(paciente.getCpf());

       if(pacienteCpf.isPresent()){
           if(!pacienteCpf.get().getId().equals(paciente.getId())){
               existCpf = true;
           }
       }

       if(existCpf){
           throw  new BusinessException("CPF já cadastrado.");
       }

        return  repository.save(paciente);
    }
    public Paciente alterar(Long id, Paciente paciente) {
        Optional<Paciente> optPaciente = this.buscarPorId(id);

        if (optPaciente.isEmpty()) {
            throw new BusinessException("Paciente não cadastrado!");
        }

        paciente.setId(id);

        return salvar(paciente);
    }

    public List<Paciente> listarTodos(){
        return repository.findAll();
    }

    public Optional <Paciente> buscarPorId(Long id){
        return repository.findById(id);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

}