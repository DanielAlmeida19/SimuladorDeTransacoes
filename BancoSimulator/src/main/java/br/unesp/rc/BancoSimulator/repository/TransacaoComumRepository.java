package br.unesp.rc.BancoSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BancoSimulator.model.TransacaoComum;

public interface TransacaoComumRepository extends JpaRepository<TransacaoComum, Long>{

    
}
