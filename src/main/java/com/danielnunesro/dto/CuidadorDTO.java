package com.danielnunesro.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CuidadorDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String lastName;
	
	private LocalDate birthday;
	
	private String cpf;
	
	private String rg;
	
	private String telefone;
	
	private String email;
	
	public CuidadorDTO() {
		
	}

	public CuidadorDTO(String name, String lastName, LocalDate birthday, String cpf, String rg, String telefone,
			String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.birthday = birthday;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
