package br.unesp.rc.BancoSimulator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.StatusTransacao;
import br.unesp.rc.BancoSimulator.model.TipoTransacao;
import br.unesp.rc.BancoSimulator.model.Investimento;

public record InvestimentoDTO(
    long id,
    TipoTransacao tipoTransacao,
    BigDecimal valor,
    LocalDateTime horario,
    StatusTransacao statusTransacao,
    long contaOrigemId,
    long ativoId
) {
    public Investimento toInvestimento(Conta contaOrigem){
        Investimento investimento = new Investimento();

        investimento.setId(id);
        investimento.setValue(valor);
        investimento.setContaOrigem(contaOrigem);
        investimento.setDateTime(horario);
        investimento.setTipoTransacao(tipoTransacao);
        investimento.setStatusTransacao(statusTransacao);
        investimento.setIdAtivo(ativoId);

        return investimento;
    }

    public static InvestimentoDTO fromInvestimento(Investimento investimento) {
        return new InvestimentoDTO(
            investimento.getId(), 
            investimento.getTipoTransacao(), 
            investimento.getValue(), 
            investimento.getDateTime(), 
            investimento.getStatusTransacao(), 
            investimento.getContaOrigem().getId(), 
            investimento.getIdAtivo()
        );
    }
}
