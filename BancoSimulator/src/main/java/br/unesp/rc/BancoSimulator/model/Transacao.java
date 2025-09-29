package br.unesp.rc.BancoSimulator.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe de modelo que representa a entidade de Transação, armazenada pelo banco, pela bolsa e pelo FGC.
 *
 * @author Daniel
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "categoria_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        name = "tipo_transacao",
        nullable = false
    )
    private TipoTransacao tipoTransacao;

    @Column(
        name = "value",
        nullable = false,
        precision = 2
    )
    private float value;

    @Column(
        name = "date_time",
        columnDefinition = "TIMESTAMP(6)",
        nullable = false
    )
    private LocalDateTime dateTime;

    @Column(
        name = "status_transacao",
        nullable = false
    )
    private StatusTransacao statusTransacao;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @Column(
        name = "categoria_transacao",
        insertable = false,
        updatable = false
    )
    private String categoriaTransacao;
}
