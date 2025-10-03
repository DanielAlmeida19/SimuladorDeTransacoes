package br.unesp.rc.BolsaValoresSimulator.model;

import java.time.LocalDateTime;

public class Transacao {

    private long id;
    private long idPessoa;
    private long idAtivo;
    private TipoTransacao tipoTransacao;
    private float valor;
    private LocalDateTime data;
    private StatusTransacao statusTransacao;
}
