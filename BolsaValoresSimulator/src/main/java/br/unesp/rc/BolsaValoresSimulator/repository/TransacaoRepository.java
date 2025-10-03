package br.unesp.rc.BolsaValoresSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BolsaValoresSimulator.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    
}
