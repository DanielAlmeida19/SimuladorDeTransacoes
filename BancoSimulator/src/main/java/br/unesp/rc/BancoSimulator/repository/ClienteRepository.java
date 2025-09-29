package br.unesp.rc.BancoSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BancoSimulator.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    
}
