package br.unesp.rc.FGCSimulator.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FGC {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        name = "valor_disponivel",
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal valorDisponivel;

    @Column(
        name = "valor_seguravel",
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal valorSeguravel;

    @JsonIgnore
    private List<String> regras;
}
