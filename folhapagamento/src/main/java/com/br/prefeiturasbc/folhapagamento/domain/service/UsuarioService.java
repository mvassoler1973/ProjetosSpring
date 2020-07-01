package com.br.prefeiturasbc.folhapagamento.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.prefeiturasbc.folhapagamento.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

}
