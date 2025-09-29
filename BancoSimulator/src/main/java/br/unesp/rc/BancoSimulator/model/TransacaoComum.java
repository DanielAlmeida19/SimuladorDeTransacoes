package br.unesp.rc.BancoSimulator.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@DiscriminatorValue("comum")
public class TransacaoComum extends Transacao{

    @ManyToOne
    @JoinColumn(
        name = "conta_destino",
        nullable = false
    )
    private Conta contaDestino;
}
