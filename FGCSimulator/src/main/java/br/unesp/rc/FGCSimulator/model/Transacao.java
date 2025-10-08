package br.unesp.rc.FGCSimulator.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        name = "external_id",
        nullable = true,
        unique = true
    )
    private long externId;
    
    @Column(
        name = "id_conta",
        nullable = false
    )
    private long idPessoa;

    @Column(
        name = "id_ativo",
        nullable = false
    )
    private long idAtivo;

    @Column(
        name = "tipo_transacao",
        nullable = false
    )
    private TipoTransacao tipoTransacao;

    @Column(
        name = "external_tipo_transacao",
        nullable = false
    )
    private String externalTipoTransacao;

    @Column(
        name = "status_transacao",
        nullable = false
    )
    private StatusTransacao statusTransacao;

    @Column(
        name = "valor",
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal valor;

    @Column(
        name = "horario",
        nullable = false,
        columnDefinition = "TIMESTAMP(6)"
    )
    private LocalDateTime horario;

}
