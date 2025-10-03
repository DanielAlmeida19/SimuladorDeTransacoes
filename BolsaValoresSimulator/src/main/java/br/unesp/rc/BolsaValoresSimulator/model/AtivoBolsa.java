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
public class AtivoBolsa extends Ativo {

    private float valorCota;
    private float valorPatrimonial;
    private int cotasNegociaveis;
    private float ultimoProvento;
    private TipoAtivo tipoAtivo;
}
