package com.br.prefeiturasbc.folhapagamento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.prefeiturasbc.folhapagamento.domain.model.GrupoAcessoSistema;

@Repository
public interface GrupoAcessoSistemaRepository extends JpaRepository<GrupoAcessoSistema, Long> {

}
