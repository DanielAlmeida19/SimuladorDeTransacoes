package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.BancoSimulator.dto.TransacaoComumDTO;
import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.StatusTransacao;
import br.unesp.rc.BancoSimulator.model.TransacaoComum;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.TransacaoComumRepository;

@Service
public class TransacaoComumService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String baseTopic = "TransacaoComum";
    private static final String group = "my-group";

    private static final String pendentTopic = baseTopic + ".request";
    private static final String processTopic = baseTopic + ".reply";

    @Autowired
    TransacaoComumRepository transacaoRepository;

    @Autowired
    ContaService contaService;
    
    /**
     * Este é o método inicial que lança a requisição de transação para a outra ponta da comunicação 
     * Ele não atualiza nenhuma das duas contas, apenas envia o objeto para a outra conta realizar
     * a transação e enviar a confirmação
     */
    public TransacaoComum transacaoPendenteProducer(TransacaoComumDTO transacaoComumDTO) {

        // Vamos converter o objeto dto em um objeto real
        Conta contaOrigem = contaService.findById(transacaoComumDTO.contaOrigemId());
        Conta contaDestino = contaService.findById(transacaoComumDTO.contaDestinoId());

        // Se a temos ambas as contas, pq não atualizamos as duas agora?
        // E SE ESTIVESSEM EM BANCOS DIFERENTES?
        // Um banco teria que se comunicar com o outro, e é isto que estamos tentando simular, 
        // cenários de messageria onde isso seria necessário
        TransacaoComum transacao = transacaoComumDTO.toTransacaoComum(contaOrigem, contaDestino);

        // Ao recebermos uma requisição de transação, fazemos com que esta seja pendente.
        transacao.setStatusTransacao(StatusTransacao.PENDENTE);

        // Armazenamos o estado atual da transação no banco de dados 
        TransacaoComum newTransacao = save(transacao);

        try {
            transacaoComumDTO = TransacaoComumDTO.fromTransacaoComum(newTransacao);
            String json = objectMapper.writeValueAsString(transacaoComumDTO);

            System.out.println("Estou enviando a transação");
            kafkaTemplate.send(pendentTopic, json);
            
        }catch (Exception e) {
            throw new RuntimeException("Erro no fluxo de messageria: " + e);
        }
        // try {
        //     transacaoComumDTO = TransacaoComumDTO.fromTransacaoComum(newTransacao);
        //     String json = objectMapper.writeValueAsString(transacaoComumDTO);
        //     kafkaTemplate.send(pendentTopic, json);
        // } catch (Exception e) {
        //     throw new RuntimeException("Erro ao desserializar transação: " + e);
        // }



        return newTransacao;
    }

    @KafkaListener(topics = pendentTopic, groupId = group)
    @SendTo(processTopic)
    public String transacaoPendenteConsumer(String message) {
        try {
            TransacaoComumDTO transacaoComumDTO = objectMapper.readValue(message, TransacaoComumDTO.class);
            Conta contaOrigem = contaService.findById(transacaoComumDTO.contaOrigemId());
            Conta contaDestino = contaService.findById(transacaoComumDTO.contaDestinoId());

            contaDestino.setSaldo(contaDestino.getSaldo().add(transacaoComumDTO.valor()));
            TransacaoComum transacao = transacaoComumDTO.toTransacaoComum(contaOrigem, contaDestino);
            transacao.setStatusTransacao(StatusTransacao.PROCESSANDO);

            // Persistindo a transacao e retornando
            TransacaoComum transacaoComum = save(transacao);
            contaService.update(contaDestino);
            transacaoComumDTO = TransacaoComumDTO.fromTransacaoComum(transacaoComum);
            return objectMapper.writeValueAsString(transacaoComumDTO);
        } catch (Exception e) {
            return new Error(e).toString();
        }
    }

    @KafkaListener(topics = processTopic, groupId = group)
    public void transacaoProcessConsumer(String message) {
        try {
            TransacaoComumDTO transacaoComumDTO = objectMapper.readValue(message, TransacaoComumDTO.class);
            Conta contaOrigem = contaService.findById(transacaoComumDTO.contaOrigemId());
            Conta contaDestino = contaService.findById(transacaoComumDTO.contaDestinoId());

            // Atualizando a conta de origem da transação
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transacaoComumDTO.valor()));
            TransacaoComum transacao = transacaoComumDTO.toTransacaoComum(contaOrigem, contaDestino);
            transacao.setStatusTransacao(StatusTransacao.FINALIZADA);

            // Persistindo a transacao e retornando
            save(transacao);
            contaService.update(contaOrigem);
        } catch (Exception e) {
            System.out.println("Erro na transação: " + e);
        }
        
    }

    public List<TransacaoComum> findAll() {
        List<TransacaoComum> transacoes;
        
        transacoes = new ArrayList<>();
        transacoes = transacaoRepository.findAll();

        return transacoes;
    }

    public TransacaoComum findById(long id) {
        Optional<TransacaoComum> existingTransacao = transacaoRepository.findById(id);
        
        if (existingTransacao.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
            }

        return existingTransacao.get();

    }

    public TransacaoComum save(TransacaoComum transacao) {
        TransacaoComum newTransacao = transacaoRepository.save(transacao);
        return newTransacao;
    }

    public TransacaoComum update(TransacaoComum transacao){

        TransacaoComum updatedTransacao = null;

        TransacaoComum oldTransacao = findById(transacao.getId());
        EntityMapper.update(oldTransacao, transacao);
        updatedTransacao = transacaoRepository.save(oldTransacao);

        return updatedTransacao;
    }

    public void delete(long id) {
        transacaoRepository.deleteById(id);
    }
}
