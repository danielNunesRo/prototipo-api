package com.danielnunesro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.danielnunesro.dto.CuidadorDTO;
import com.danielnunesro.exceptions.ExistingUser;
import com.danielnunesro.exceptions.ResourceNotFoundException;
import com.danielnunesro.mapper.CuidadorMapper;
import com.danielnunesro.repositories.CuidadorRepository;

@Service
public class CuidadorService {
	
	@Autowired
	private CuidadorRepository cuidadorRepository;
	
	
	public List<CuidadorDTO> findAll() {
		List<CuidadorDTO> list = CuidadorMapper.INSTANCE.toClienteDTOList(cuidadorRepository.findAll());
		return list;
	}
	
	public CuidadorDTO findById(Long id) {
		var existingCuidador = cuidadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não achamos nenhum cuidador com esse ID"));
		CuidadorDTO cuidador = CuidadorMapper.INSTANCE.toCuidadorDTO(existingCuidador);
		return cuidador;
	}
	
	@Cacheable("findbycpf")
	public CuidadorDTO findByCpf(String cpf) {
		var existingCuidador = cuidadorRepository.findByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Não achamos nenhum cuidador com esse ID"));
		CuidadorDTO cuidador = CuidadorMapper.INSTANCE.toCuidadorDTO(existingCuidador);
		return cuidador;
	}
	
	public CuidadorDTO create(CuidadorDTO newCuidador) {
		var existingCuidador = cuidadorRepository.findByCpf(newCuidador.getCpf());
		
		if(!existingCuidador.isEmpty()) {
			throw new ExistingUser("Há um cuidador cadastrado com CPF!");
		}
		
		var cuidador = CuidadorMapper.INSTANCE.toCuidador(newCuidador);
		cuidadorRepository.save(cuidador);
		
		return CuidadorMapper.INSTANCE.toCuidadorDTO(cuidador);
	}
	
	public CuidadorDTO update(String cpf, CuidadorDTO cuidadorUpdate) {
		var existingCuidador = cuidadorRepository.findByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Não achamos nenhum cuidador com esse ID"));
		
		existingCuidador.setName(cuidadorUpdate.getName());
		existingCuidador.setLastName(cuidadorUpdate.getLastName());
		existingCuidador.setEmail(cuidadorUpdate.getEmail());
		existingCuidador.setCpf(cuidadorUpdate.getCpf());
		existingCuidador.setRg(cuidadorUpdate.getRg());
		existingCuidador.setBirthday(cuidadorUpdate.getBirthday());
		existingCuidador.setTelefone(cuidadorUpdate.getTelefone());
		
		cuidadorRepository.save(existingCuidador);
		return CuidadorMapper.INSTANCE.toCuidadorDTO(existingCuidador);
		
		
    }
	
	public void delete(Long id) {
		var existingCuidador = cuidadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não achamos nenhum cuidador com esse ID"));
		cuidadorRepository.deleteById(id);
	}
	
	
	
	
}
