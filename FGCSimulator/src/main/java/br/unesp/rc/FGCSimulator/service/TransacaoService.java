package br.unesp.rc.FGCSimulator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.FGCSimulator.dto.TransacaoInvestimentoDTO;
import br.unesp.rc.FGCSimulator.model.FGC;
import br.unesp.rc.FGCSimulator.model.StatusTransacao;
import br.unesp.rc.FGCSimulator.model.Transacao;
import br.unesp.rc.FGCSimulator.model.mapper.EntityMapper;
import br.unesp.rc.FGCSimulator.repository.FGCRepository;
import br.unesp.rc.FGCSimulator.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String baseTopic = "Investimento";
    private static final String group = "my-group";

    private static final String pendentTopic = baseTopic + ".requestFGC";
    private static final String processTopic = baseTopic + ".replyFGC";

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    FGCRepository fgcRepository;

    @KafkaListener(topics = pendentTopic, groupId = group)
    public void transacaoPendenteProducer(String message) {
        try {
            TransacaoInvestimentoDTO transacaoInvestimentoDTO = objectMapper.readValue(
                message,
                TransacaoInvestimentoDTO.class
            );
            Transacao transacao = transacaoInvestimentoDTO.toTransacao();

            FGC fgc = fgcRepository.findAll().getFirst();
            if (transacaoInvestimentoDTO.valor().compareTo(fgc.getValorSeguravel()) > 0) {
                System.out.println("Está pedindo demais campeão");
                transacao.setStatusTransacao(StatusTransacao.CANCELADA);
            } else {
                transacao.setStatusTransacao(StatusTransacao.PROCESSANDO);
            }

            BigDecimal resultado = fgc.getValorDisponivel().subtract(transacao.getValor());
            System.out.println("Valor a ser retirado do FGC: R$" + resultado);
            fgc.setValorDisponivel(resultado);
            fgcRepository.save(fgc);
            Transacao newTransacao = transacaoRepository.save(transacao);
            transacaoInvestimentoDTO = TransacaoInvestimentoDTO.fromTansacao(newTransacao);
            String json = objectMapper.writeValueAsString(transacaoInvestimentoDTO);

            System.out.println("Investe ae fera");
            System.out.println(json);
            kafkaTemplate.send(processTopic, json);
        } catch (Exception e) {
            throw new RuntimeException("Erro no fluxo de messageria", e);
        }

    }

    public List<Transacao> findAll() {
        List<Transacao> transacoes;

        transacoes = new ArrayList<>();
        transacoes = transacaoRepository.findAll();

        return transacoes;
    }

    public Transacao findById(long id) {
        Optional<Transacao> existingTransacao = transacaoRepository.findById(id);

        if (existingTransacao.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
            }

        return existingTransacao.get();

    }

    public Transacao save(Transacao transacao) {
        Transacao newTransacao = transacaoRepository.save(transacao);
        return newTransacao;
    }

    public Transacao update(Transacao transacao){

        Transacao updatedTransacao = null;

        Transacao oldTransacao = findById(transacao.getId());
        EntityMapper.update(oldTransacao, transacao);
        updatedTransacao = transacaoRepository.save(oldTransacao);

        return updatedTransacao;
    }

    public void delete(long id) {
        transacaoRepository.deleteById(id);
    }
}

