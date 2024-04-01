package com.danielnunesro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielnunesro.dto.CuidadorDTO;
import com.danielnunesro.services.CuidadorService;

@RestController
@RequestMapping("/api/cuidador")
public class CuidadorController {
	
	@Autowired
    private CuidadorService cuidadorService;

    @GetMapping
    public ResponseEntity<List<CuidadorDTO>> findAll() {
        List<CuidadorDTO> cuidadores = cuidadorService.findAll();
        return ResponseEntity.ok(cuidadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuidadorDTO> findById(@PathVariable Long id) {
        CuidadorDTO cuidador = cuidadorService.findById(id);
        return ResponseEntity.ok(cuidador);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CuidadorDTO> findByCpf(@PathVariable String cpf) {
        CuidadorDTO cuidador = cuidadorService.findByCpf(cpf);
        return ResponseEntity.ok(cuidador);
    }

    @PostMapping
    public ResponseEntity<CuidadorDTO> create(@RequestBody CuidadorDTO newCuidador) {
        CuidadorDTO cuidador = cuidadorService.create(newCuidador);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuidador);
    }
    
    @PutMapping("/{cpf}/update")
    public ResponseEntity<CuidadorDTO> update(@PathVariable String cpf, @RequestBody CuidadorDTO cuidadorUpdate) {
        CuidadorDTO updatedCuidador = cuidadorService.update(cpf, cuidadorUpdate);
        return new ResponseEntity<>(updatedCuidador, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuidadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
	
}
