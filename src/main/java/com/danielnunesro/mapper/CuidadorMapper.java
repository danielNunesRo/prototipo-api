package com.danielnunesro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.danielnunesro.dto.CuidadorDTO;
import com.danielnunesro.entities.Cuidador;

@Mapper
public interface CuidadorMapper {
	
	CuidadorMapper INSTANCE = Mappers.getMapper(CuidadorMapper.class);
	
	
	CuidadorDTO toCuidadorDTO(Cuidador cuidador);
	
	@Mapping(target = "id", ignore = true)
	Cuidador toCuidador(CuidadorDTO cuidadorDTO);
	
	List<CuidadorDTO> toClienteDTOList(List<Cuidador> clientes);
	
}
