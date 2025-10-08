package br.unesp.rc.BancoSimulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@DiscriminatorValue("investimento")
public class Investimento extends Transacao{
    
    @Column(
        name = "id_ativo",
        nullable = false
    )
    private String idAtivo;
}
