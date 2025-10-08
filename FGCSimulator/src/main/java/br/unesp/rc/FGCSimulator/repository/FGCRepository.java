package br.unesp.rc.FGCSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.FGCSimulator.model.FGC;

public interface FGCRepository extends JpaRepository<FGC, Long>{

    
}
