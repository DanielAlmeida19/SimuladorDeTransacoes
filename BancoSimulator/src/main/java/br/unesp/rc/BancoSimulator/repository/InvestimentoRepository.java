package br.unesp.rc.BancoSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BancoSimulator.model.Investimento;

public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

    
}
