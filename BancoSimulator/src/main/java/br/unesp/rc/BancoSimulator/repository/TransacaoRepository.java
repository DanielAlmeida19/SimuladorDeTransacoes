package br.unesp.rc.BancoSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BancoSimulator.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    
}
