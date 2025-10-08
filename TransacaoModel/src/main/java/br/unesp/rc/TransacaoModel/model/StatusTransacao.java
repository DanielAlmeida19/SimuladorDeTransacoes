package br.unesp.rc.BancoSimulator.model;

/**
 * Enum que representa o estado atual da transação, isso será de suma importância para o gerenciamento das mensagens
 *
 * @author Daniel
 */
public enum StatusTransacao {

    /**
     * A requisição de transação foi feita, é assim como ela fica armazenada na conta de origem
     */
    PENDENTE,

    /**
     * A transação foi aceita pelo FGC, é assim como ficará armazenada no mesmo até o fim (confirmação)
     */
    PROCESSANDO,

    /**
     * A transação ocorreu com sucesso até o destino, fica armazenada assim até chegar a confirmação da confirmação
     */
    CONFIRMADA,

    /**
     * A transação confirmou com sucesso na origem, e confimou com sucesso no destino.
     */
    FINALIZADA,

    /**
     * A transação foi interrompida sem erros, alguma regra de negócio ou desistência, serve também para cancelar o sinal de confirmação da confirmação
     */
    CANCELADA,

    /**
     * A transação falhou, irá para uma fila de processos mortos
     */
    FALHOU
}
