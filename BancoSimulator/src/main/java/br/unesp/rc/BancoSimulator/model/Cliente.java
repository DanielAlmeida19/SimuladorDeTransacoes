package br.unesp.rc.BancoSimulator.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe que modelo um cliente do banco, que faz requisições de transferências
 * ou de sua conta para de outro cliente
 * ou de sua conta para um ativo de investimento
 *
 * @author Daniel
 */
@Entity(name = "cliente")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(
        name = "nome",
        length = 50
    )
    private String nome;

    @Column(
        name = "cpf",
        unique = true
    )
    private String cpf;

    @Column(
        name = "email",
        unique = true
    )
    private String email;

    @Column(
        name = "senha"
    )
    private String senha;


    @JsonIgnore
    @OneToMany(
        mappedBy = "cliente",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Conta> contas;
}
