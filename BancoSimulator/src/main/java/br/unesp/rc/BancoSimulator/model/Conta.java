package br.unesp.rc.BancoSimulator.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe de modelo que representa a conta bancária associada a um cliente
 *
 * @author Daniel
 */
@Entity
@Table(
    name = "conta",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"agencia", "numero"})
    }
)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "agencia")
    private String agencia;

    @Column(
        name = "tipo",
        nullable = false
    )
    private TipoConta tipoConta;

    @Column(
        name = "saldo",
        nullable = false,
        precision = 2
    )
    private float saldo;

    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Cliente cliente;
}
