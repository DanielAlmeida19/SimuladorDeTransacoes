package br.unesp.rc.BancoSimulator.model;

import java.time.LocalDateTime;

public class NotificadorEmail implements Notificacao {

    @Override
    public void sendMessage(long id, String message, LocalDateTime dateTime, Transacao transacao) {
        // TODO: Isso aqui nem funciona
    }
}
