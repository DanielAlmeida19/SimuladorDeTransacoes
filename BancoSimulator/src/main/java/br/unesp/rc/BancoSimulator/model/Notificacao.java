package br.unesp.rc.BancoSimulator.model;

import java.time.LocalDateTime;

public interface Notificacao {

    public void sendMessage(long id, String message, LocalDateTime dateTime, Transacao transacao);
}
