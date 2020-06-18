package com.br.mvassoler.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.mvassoler.food.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findById(long id);

	List<Cliente> findByNome(String nome);

	List<Cliente> findByNomeContaining(String nome);

	List<Cliente> findByCep(Integer cep);

}
