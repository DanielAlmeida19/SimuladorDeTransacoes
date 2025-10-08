package br.unesp.rc.BancoSimulator.model;

/**
 * Enum que mapeia o tipo da transação </br>
 * <b>Não será importante nesse estudo de caso, seria apenas no caso de regras de negócio específicas</b>
 *
 * @author Daniel
 */
public enum TipoTransacao {

    PIX,
    TED,
    DOC,
    PAGAMENTO,
    DEBITO_AUTOMATICO
}
