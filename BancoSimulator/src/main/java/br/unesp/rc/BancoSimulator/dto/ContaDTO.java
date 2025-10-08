package br.unesp.rc.BancoSimulator.dto;



import java.math.BigDecimal;

import br.unesp.rc.BancoSimulator.model.Cliente;
import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.TipoConta;

public record ContaDTO(
    long id,
    String numero,
    String agencia,
    TipoConta tipoConta,
    BigDecimal saldo,
    long clienteId
    ) {

    public Conta toConta(Cliente cliente) {
        Conta conta = new Conta();
        conta.setId(id);
        conta.setSaldo(saldo);
        conta.setNumero(numero);
        conta.setAgencia(agencia);
        conta.setCliente(cliente);
        conta.setTipoConta(tipoConta);

        return conta;
    }

    public static ContaDTO fromConta(Conta conta){
        return new ContaDTO(
            conta.getId(), 
            conta.getNumero(),
            conta.getAgencia(), 
            conta.getTipoConta(), 
            conta.getSaldo(), 
            conta.getCliente().getId()
        );
    }
}
