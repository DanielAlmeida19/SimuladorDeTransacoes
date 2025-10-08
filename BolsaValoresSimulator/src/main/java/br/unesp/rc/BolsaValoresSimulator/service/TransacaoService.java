package br.unesp.rc.BolsaValoresSimulator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.BolsaValoresSimulator.dto.InvestimentoDTO;
import br.unesp.rc.BolsaValoresSimulator.model.AtivoBolsa;
import br.unesp.rc.BolsaValoresSimulator.model.Transacao;
import br.unesp.rc.BolsaValoresSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BolsaValoresSimulator.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String baseTopic = "Investimento";

    private static final String group = "my-group";


    private static final String confirmTopic = baseTopic + ".requestBolsa";
    private static final String finalizeTopic = baseTopic + ".replyBolsa";

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    AtivoBolsaService ativoBolsaService;

    @KafkaListener(topics = confirmTopic, groupId = group)
    public void investimentoListener(String message) {
        try {
            
            InvestimentoDTO investimentoDTO = objectMapper.readValue(message, InvestimentoDTO.class);
            AtivoBolsa ativo = ativoBolsaService.findById(investimentoDTO.ativoId());
            Transacao transacao = investimentoDTO.toTransacao(ativo);

            BigDecimal[] resultado = transacao.getValor().divideAndRemainder(ativo.getValorCota());
            System.out.println("Cotas compradas: " + resultado[0]);
            System.out.println("Sobra : R$" + resultado[1].setScale(2, RoundingMode.HALF_UP));

            ativo.setCotasNegociaveis(ativo.getCotasNegociaveis() - resultado[0].intValue());
            BigDecimal result = transacao.getValor().subtract(resultado[1]);
            System.out.println("Valor final da transação: R$" + result);

            transacao.setValor(result);

            Transacao newTransacao = transacaoRepository.save(transacao);
            ativoBolsaService.save(ativo);

            investimentoDTO = InvestimentoDTO.fromTransacao(newTransacao);
            String json = objectMapper.writeValueAsString(investimentoDTO);

            System.out.println("Dinheiro investido, devolvendo pra não dever pra pobre");
            kafkaTemplate.send(finalizeTopic, json);
        } catch (Exception e) {
            throw new RuntimeException("Erro no fluxo de messageria", e);
        }
    }

    public List<Transacao> findAll() {
        List<Transacao> transacaos;

        transacaos = new ArrayList<>();
        transacaos = transacaoRepository.findAll();

        return transacaos;
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

