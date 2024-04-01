package com.danielnunesro.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielnunesro.dto.CuidadorDTO;
import com.danielnunesro.entities.Cuidador;
import com.danielnunesro.exceptions.ExistingUser;
import com.danielnunesro.exceptions.ResourceNotFoundException;
import com.danielnunesro.repositories.CuidadorRepository;
import com.danielnunesro.services.CuidadorService;

@ExtendWith(MockitoExtension.class)
public class CuidadorServiceTest {
	
	
	@Mock
    private CuidadorRepository cuidadorRepository;

    @InjectMocks
    private CuidadorService cuidadorService;
    
    Cuidador cuidadorMockado = new Cuidador("João", "Silva", LocalDate.of(1990, 5, 15),
            "12345678900", "123456", "123456789", "joao.silva@example.com");

    @Test
    void testCreate() {
        String cpf = "12345678900";
        CuidadorDTO newCuidadorDTO = new CuidadorDTO("João", "Silva", LocalDate.of(1990, 5, 15),cpf, "123456", "123456789", "joao.silva@example.com");

        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        when(cuidadorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CuidadorDTO result = cuidadorService.create(newCuidadorDTO);

        verify(cuidadorRepository, times(1)).findByCpf(cpf);

        verify(cuidadorRepository, times(1)).save(any());

        assertNotNull(result);

        assertEquals(newCuidadorDTO.getName(), result.getName());
        assertEquals(newCuidadorDTO.getLastName(), result.getLastName());
        assertEquals(newCuidadorDTO.getBirthday(), result.getBirthday());
        assertEquals(newCuidadorDTO.getCpf(), result.getCpf());
        assertEquals(newCuidadorDTO.getRg(), result.getRg());
        assertEquals(newCuidadorDTO.getTelefone(), result.getTelefone());
        assertEquals(newCuidadorDTO.getEmail(), result.getEmail());
    }
    
    @Test
    void testCreate_ExistingUserException() {
        String cpf = "12345678900";
        CuidadorDTO newCuidadorDTO = new CuidadorDTO("João", "Silva", LocalDate.of(1990, 5, 15),
                cpf, "123456", "123456789", "joao.silva@example.com");

        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.of(cuidadorMockado));

        assertThrows(ExistingUser.class, () -> cuidadorService.create(newCuidadorDTO));

        verify(cuidadorRepository, times(1)).findByCpf(cpf);

        verify(cuidadorRepository, never()).save(any());
    }
    
    @Test
    void testFindAll() {
        List<Cuidador> cuidadores = Arrays.asList(
            new Cuidador("João", "Silva", LocalDate.of(1990, 5, 15),
                    "12345678900", "123456", "123456789", "joao.silva@example.com"),
            new Cuidador("Maria", "Santos", LocalDate.of(1985, 9, 20),
                    "98765432100", "654321", "987654321", "maria.santos@example.com")
        );
        when(cuidadorRepository.findAll()).thenReturn(cuidadores);

        List<CuidadorDTO> result = cuidadorService.findAll();

        assertNotNull(result);

        assertEquals(cuidadores.size(), result.size());

    }

    @Test
    void testFindById() {
        Long id = 1L;
        Cuidador cuidadorMockado = new Cuidador("João", "Silva", LocalDate.of(1990, 5, 15),
                "12345678900", "123456", "123456789", "joao.silva@example.com");
        when(cuidadorRepository.findById(id)).thenReturn(Optional.of(cuidadorMockado));

        CuidadorDTO result = cuidadorService.findById(id);

        assertNotNull(result);

    }

    @Test
    void testFindByCpf() {
        String cpf = "12345678900";
        Cuidador cuidadorMockado = new Cuidador("João", "Silva", LocalDate.of(1990, 5, 15),
                cpf, "123456", "123456789", "joao.silva@example.com");
        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.of(cuidadorMockado));

        CuidadorDTO result = cuidadorService.findByCpf(cpf);

        assertNotNull(result);

    }
    
    @Test
    void testFindById_ResourceNotFoundException() {
        Long id = 1L;
        when(cuidadorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuidadorService.findById(id));
    }

    @Test
    void testFindByCpf_ResourceNotFoundException() {
        String cpf = "12345678900";
        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuidadorService.findByCpf(cpf));
    }
    
    @Test
    void testUpdate() {
        String cpf = "12345678900";
        Cuidador cuidadorExistente = new Cuidador("João", "Silva", LocalDate.of(1990, 5, 15),
                cpf, "123456", "123456789", "joao.silva@example.com");
        CuidadorDTO cuidadorUpdateDTO = new CuidadorDTO("João", "Oliveira", LocalDate.of(1990, 5, 15),
                cpf, "654321", "987654321", "joao.oliveira@example.com");

        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.of(cuidadorExistente));

        when(cuidadorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CuidadorDTO result = cuidadorService.update(cpf, cuidadorUpdateDTO);

        verify(cuidadorRepository, times(1)).findByCpf(cpf);

        assertEquals(cuidadorUpdateDTO.getName(), cuidadorExistente.getName());
        assertEquals(cuidadorUpdateDTO.getLastName(), cuidadorExistente.getLastName());
        assertEquals(cuidadorUpdateDTO.getEmail(), cuidadorExistente.getEmail());
        assertEquals(cuidadorUpdateDTO.getCpf(), cuidadorExistente.getCpf());
        assertEquals(cuidadorUpdateDTO.getRg(), cuidadorExistente.getRg());
        assertEquals(cuidadorUpdateDTO.getBirthday(), cuidadorExistente.getBirthday());
        assertEquals(cuidadorUpdateDTO.getTelefone(), cuidadorExistente.getTelefone());

        verify(cuidadorRepository, times(1)).save(cuidadorExistente);

        assertEquals(cuidadorUpdateDTO.getName(), result.getName());
        assertEquals(cuidadorUpdateDTO.getLastName(), result.getLastName());
        assertEquals(cuidadorUpdateDTO.getEmail(), result.getEmail());
        assertEquals(cuidadorUpdateDTO.getCpf(), result.getCpf());
        assertEquals(cuidadorUpdateDTO.getRg(), result.getRg());
        assertEquals(cuidadorUpdateDTO.getBirthday(), result.getBirthday());
        assertEquals(cuidadorUpdateDTO.getTelefone(), result.getTelefone());
    }
    
    @Test
    void testUpdate_ResourceNotFoundException() {
        String cpf = "12345678900";
        CuidadorDTO cuidadorUpdateDTO = new CuidadorDTO("João", "Oliveira", LocalDate.of(1990, 5, 15),
                cpf, "654321", "987654321", "joao.oliveira@example.com");

        when(cuidadorRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuidadorService.update(cpf, cuidadorUpdateDTO));

        verify(cuidadorRepository, times(1)).findByCpf(cpf);

        verify(cuidadorRepository, never()).save(any());
    }
    
    @Test
    void testDelete() {
        Long id = 1L;
        when(cuidadorRepository.findById(id)).thenReturn(Optional.of(new Cuidador()));

        cuidadorService.delete(id);

        verify(cuidadorRepository, times(1)).findById(id);

        verify(cuidadorRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete_ResourceNotFoundException() {
        Long id = 1L;
        when(cuidadorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cuidadorService.delete(id));

        verify(cuidadorRepository, times(1)).findById(id);

        verify(cuidadorRepository, never()).deleteById(any());
    }
}
