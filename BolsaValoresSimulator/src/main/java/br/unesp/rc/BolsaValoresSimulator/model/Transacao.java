package br.unesp.rc.BolsaValoresSimulator.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        name = "id_pessoa",
        nullable = false
    )
    private long idPessoa;

    @ManyToOne
    @JoinColumn(
        name = "id_ativo",
        nullable = false
    )
    private Ativo ativo;

    @Column(
        name = "tipo_transacao",
        nullable = false
    )
    private TipoTransacao tipoTransacao;

    @Column(
        name = "valor",
        nullable = false,
        precision = 2
    )
    private float valor;

    @Column(
        name = "data",
        columnDefinition = "TIMESTAMP(6)",
        nullable = false
    )
    private LocalDateTime data;

    @Column(
        name = "status_transacao",
        nullable = false
    )
    private StatusTransacao statusTransacao;
}
