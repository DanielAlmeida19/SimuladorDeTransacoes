package br.unesp.rc.BancoSimulator.dto;

import java.time.LocalDateTime;

import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.StatusTransacao;
import br.unesp.rc.BancoSimulator.model.TipoTransacao;
import br.unesp.rc.BancoSimulator.model.TransacaoComum;

public record TransacaoComumDTO(
    long id,
    TipoTransacao tipoTransacao,
    float valor,
    LocalDateTime horario,
    StatusTransacao statusTransacao,
    long contaOrigemId,
    long contaDestinoId
) {
    public TransacaoComum toTransacaoComum(Conta contaOrigem, Conta contaDestino){
        TransacaoComum transacaoComum = new TransacaoComum();

        transacaoComum.setId(id);
        transacaoComum.setValue(valor);
        transacaoComum.setContaOrigem(contaOrigem);
        transacaoComum.setContaDestino(contaDestino);
        transacaoComum.setDateTime(horario);
        transacaoComum.setTipoTransacao(tipoTransacao);
        transacaoComum.setStatusTransacao(statusTransacao);

        return transacaoComum;
    }

    public static TransacaoComumDTO fromTransacaoComum(TransacaoComum transacaoComum) {
        return new TransacaoComumDTO(
            transacaoComum.getId(), 
            transacaoComum.getTipoTransacao(), 
            transacaoComum.getValue(), 
            transacaoComum.getDateTime(), 
            transacaoComum.getStatusTransacao(), 
            transacaoComum.getContaOrigem().getId(), 
            transacaoComum.getContaDestino().getId()
        );
    }
}
