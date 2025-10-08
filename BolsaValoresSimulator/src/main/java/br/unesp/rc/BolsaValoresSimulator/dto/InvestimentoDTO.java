package br.unesp.rc.BolsaValoresSimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unesp.rc.BolsaValoresSimulator.model.Ativo;
import br.unesp.rc.BolsaValoresSimulator.model.StatusTransacao;
import br.unesp.rc.BolsaValoresSimulator.model.TipoTransacao;
import br.unesp.rc.BolsaValoresSimulator.model.Transacao;

public record InvestimentoDTO(
    long id,
    String tipoTransacao,
    TipoTransacao tipoTransacaoBolsa,
    BigDecimal valor,
    LocalDateTime horario,
    StatusTransacao statusTransacao,
    long contaOrigemId,
    long ativoId
) {
    public Transacao toTransacao(Ativo ativo) {
        Transacao transacao = new Transacao();

        transacao.setExternalTipoTransacao(tipoTransacao);
        transacao.setExternalId(id);
        transacao.setAtivo(ativo);
        transacao.setValor(valor);
        transacao.setIdPessoa(contaOrigemId);
        transacao.setData(horario);
        transacao.setTipoTransacao(TipoTransacao.INVESTIMENTO);
        transacao.setStatusTransacao(statusTransacao);

        return transacao;
    }

    public static InvestimentoDTO fromTransacao(Transacao transacao) {
        return new InvestimentoDTO(
            transacao.getExternalId(), 
            transacao.getExternalTipoTransacao(),
            transacao.getTipoTransacao(), 
            transacao.getValor(), 
            transacao.getData(), 
            transacao.getStatusTransacao(), 
            transacao.getIdPessoa(),
            transacao.getAtivo().getId()
        );
    }
}
