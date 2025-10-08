package br.unesp.rc.FGCSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.FGCSimulator.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    
}
