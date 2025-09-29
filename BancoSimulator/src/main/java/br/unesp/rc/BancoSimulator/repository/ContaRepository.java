package br.unesp.rc.BancoSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BancoSimulator.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    
}
