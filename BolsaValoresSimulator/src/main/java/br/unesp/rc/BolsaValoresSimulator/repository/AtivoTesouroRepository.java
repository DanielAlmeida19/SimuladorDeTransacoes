package br.unesp.rc.BolsaValoresSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.BolsaValoresSimulator.model.AtivoTesouro;

public interface AtivoTesouroRepository extends JpaRepository<AtivoTesouro, Long> {

    
}
