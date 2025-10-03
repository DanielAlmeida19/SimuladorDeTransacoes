package br.unesp.rc.BolsaValoresSimulator.model;

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
        name = "valor_cota",
        nullable = false,
        precision = 2
    )
    private float valorCota;

    @Column(
        name = "valor_patrimonial",
        nullable = false,
        precision = 2
    )
    private float valorPatrimonial;

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
