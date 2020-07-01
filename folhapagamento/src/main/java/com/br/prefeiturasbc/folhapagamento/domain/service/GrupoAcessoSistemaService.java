package com.br.prefeiturasbc.folhapagamento.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.prefeiturasbc.folhapagamento.domain.repository.GrupoAcessoSistemaRepository;

@Service
public class GrupoAcessoSistemaService {

	@Autowired
	private GrupoAcessoSistemaRepository grupoAcessoSistemaRepository;

	public GrupoAcessoSistemaRepository getGrupoAcessoSistemaRepository() {
		return grupoAcessoSistemaRepository;
	}

}
