package br.com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.banco.entity.Cliente;

@Repository
public interface BancoRepository extends JpaRepository<Cliente, Integer> {

}
