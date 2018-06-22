package com.prueba.areamovil.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.prueba.areamovil.backend.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
