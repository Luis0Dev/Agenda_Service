package com.IllusionODEV.Domain.Service;

import com.IllusionODEV.Domain.Entity.Usuario;
import com.IllusionODEV.Domain.Repository.UsuarioRespository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRespository respository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = respository.findByUsuario(usuario);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario não econtrado");
        }
        Usuario user = usuarioOptional.get();
        return new User(user.getUsuario(), user.getSenha(), new ArrayList<>());
    }

    public List<Usuario> getAll() {
        return respository.findAll();
    }

    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return respository.save(usuario);
    }

}
