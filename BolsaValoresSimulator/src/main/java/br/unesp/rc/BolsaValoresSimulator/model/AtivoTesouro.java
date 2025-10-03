package br.unesp.rc.BolsaValoresSimulator.model;

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
public class AtivoTesouro {

    private float valorTitulo;
    private float valorFundo;
    private float rentabilidade;
    private Indexador indexador;
}
