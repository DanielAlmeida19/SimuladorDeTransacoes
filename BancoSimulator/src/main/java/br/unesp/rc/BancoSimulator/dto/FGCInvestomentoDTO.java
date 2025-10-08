package br.unesp.rc.BancoSimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.Investimento;
import br.unesp.rc.BancoSimulator.model.StatusTransacao;
import br.unesp.rc.BancoSimulator.model.TipoTransacao;

public record FGCInvestomentoDTO(
    long id,
    TipoTransacao tipoTransacao,
    String externalTipoTransacao,
    BigDecimal valor,
    LocalDateTime horario,
    StatusTransacao statusTransacao,
    long contaOrigemId,
    long ativoId
) {
    public Investimento toInvestimento(Conta conta) {
        Investimento investimento = new Investimento();
        
        investimento.setId(id);
        investimento.setIdAtivo(ativoId);
        investimento.setValue(valor);
        investimento.setContaOrigem(conta);
        investimento.setDateTime(horario);
        investimento.setTipoTransacao(tipoTransacao);
        investimento.setStatusTransacao(statusTransacao);

        return investimento;
    }
}
