package br.unesp.rc.BolsaValoresSimulator.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class AtivoBolsa extends Ativo {

    @Column(
        name = "ticker",
        nullable = false,
        length = 6
    )
    private String ticker;

    @Column(
        name = "nome_companhia",
        nullable = false,
        length = 50
    )
    private String nomeCompanhia;

    @Column(
        name = "valor_cota",
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal valorCota;

    @Column(
        name = "valor_patrimonial",
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal valorPatrimonial;

    @Column(
        name = "cotas_negociaveis",
        nullable = false
    )
    private int cotasNegociaveis;

    @Column(
        name = "valor_provento",
        nullable = false,
        precision = 2
    )
    private float ultimoProvento;

    @Column(
        name = "tipo_ativo",
        nullable = false
    )
    private TipoAtivo tipoAtivo;
}
