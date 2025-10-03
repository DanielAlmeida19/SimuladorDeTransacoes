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
public class AtivoTesouro extends Ativo{

    @Column(
        name = "valor_titulo",
        nullable = false,
        precision = 2
    )
    private float valorTitulo;

    @Column(
        name = "valor_fundo",
        nullable = false,
        precision = 2
    )
    private float valorFundo;

    @Column(
        name = "rentabilidade",
        nullable = false,
        precision = 2
    )
    private float rentabilidade;

    @Column(
        name = "indexador",
        nullable = false
    )
    private Indexador indexador;
}
