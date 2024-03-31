package com.danielnunesro.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielnunesro.entities.Cuidador;

public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
	
	Optional<Cuidador> findByCpf(String cpf);
	
}
