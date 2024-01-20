package com.IllusionODEV.Domain.Repository;

import com.IllusionODEV.Domain.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRespository  extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
}
