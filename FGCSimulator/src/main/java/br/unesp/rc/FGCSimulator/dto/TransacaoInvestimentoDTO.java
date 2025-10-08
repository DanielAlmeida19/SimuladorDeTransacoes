package br.unesp.rc.FGCSimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unesp.rc.FGCSimulator.model.StatusTransacao;
import br.unesp.rc.FGCSimulator.model.TipoTransacao;
import br.unesp.rc.FGCSimulator.model.Transacao;

public record TransacaoInvestimentoDTO(
    long id,
    String tipoTransacao,
    TipoTransacao tipoTransacaoFGC,
    BigDecimal valor,
    LocalDateTime horario,
    StatusTransacao statusTransacao,
    long contaOrigemId,
    long ativoId
) {
    public Transacao toTransacao() {
        Transacao transacao = new Transacao();

        transacao.setExternId(id);
        transacao.setIdAtivo(ativoId);
        transacao.setIdPessoa(contaOrigemId);
        transacao.setValor(valor);
        transacao.setHorario(horario);
        transacao.setTipoTransacao(TipoTransacao.INVESTIMENTO);
        transacao.setStatusTransacao(statusTransacao);
        transacao.setExternalTipoTransacao(tipoTransacao);

        return transacao;
    }

    public static TransacaoInvestimentoDTO fromTansacao(Transacao transacao) {
        return new TransacaoInvestimentoDTO(
            transacao.getExternId(),
            transacao.getExternalTipoTransacao(),
            transacao.getTipoTransacao(), 
            transacao.getValor(), 
            transacao.getHorario(), 
            transacao.getStatusTransacao(), 
            transacao.getIdPessoa(), 
            transacao.getIdAtivo()
        );
    }
}
