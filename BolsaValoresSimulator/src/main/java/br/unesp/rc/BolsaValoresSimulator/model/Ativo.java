package br.unesp.rc.BolsaValoresSimulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ativo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
