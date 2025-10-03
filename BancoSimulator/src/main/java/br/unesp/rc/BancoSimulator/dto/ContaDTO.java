package br.unesp.rc.BancoSimulator.dto;



import br.unesp.rc.BancoSimulator.model.Cliente;
import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.TipoConta;

public record ContaDTO(
    long id,
    String numero,
    String agencia,
    TipoConta tipoConta,
    float saldo,
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
}
