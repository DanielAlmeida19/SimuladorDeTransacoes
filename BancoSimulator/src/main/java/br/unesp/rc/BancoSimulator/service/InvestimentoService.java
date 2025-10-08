package br.unesp.rc.BancoSimulator.service;

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

import br.unesp.rc.BancoSimulator.dto.ContaDTO;
import br.unesp.rc.BancoSimulator.dto.FGCInvestomentoDTO;
import br.unesp.rc.BancoSimulator.dto.InvestimentoDTO;
import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.Investimento;
import br.unesp.rc.BancoSimulator.model.StatusTransacao;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.InvestimentoRepository;

@Service
public class InvestimentoService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String baseTopic = "Investimento";

    private static final String group = "my-group";

    private static final String pendentTopic = baseTopic + ".requestFGC";
    private static final String processTopic = baseTopic + ".replyFGC";

    private static final String confirmTopic = baseTopic + ".requestBolsa";
    private static final String finalizeTopic = baseTopic + ".replyBolsa";

    @Autowired
    InvestimentoRepository investimentoRepository;

    @Autowired
    ContaService contaService;

    public Investimento investimentoPendenteProducer(InvestimentoDTO investimentoDTO){
        Conta contaOrigem = contaService.findById(investimentoDTO.contaOrigemId());       

        Investimento investimento = investimentoDTO.toInvestimento(contaOrigem);       
        investimento.setStatusTransacao(StatusTransacao.PENDENTE);
        Investimento newInvestimento = investimentoRepository.save(investimento);

        try {
            investimentoDTO = InvestimentoDTO.fromInvestimento(investimento);
            String json = objectMapper.writeValueAsString(investimentoDTO);

            System.out.println("-------------------------------------");
            System.out.println(investimento);
            System.out.println("Estou enviando o Investimento");
            System.out.println(json);
            kafkaTemplate.send(pendentTopic, json);
        } catch (Exception e) {
            throw new RuntimeException("Erro no fluxo de messageria", e);
        }


        return newInvestimento;
    }

    @KafkaListener(topics = processTopic, groupId = group)
    public void FGCResponseListener(String message) {
        try {
            FGCInvestomentoDTO investimentoDTO = objectMapper.readValue(message, FGCInvestomentoDTO.class);
            Conta contaOrigem = contaService.findById(investimentoDTO.contaOrigemId());
            Investimento investimento = investimentoDTO.toInvestimento(contaOrigem);

            investimento.setStatusTransacao(StatusTransacao.CONFIRMADA);
            Investimento newInvestimento = investimentoRepository.save(investimento);

            InvestimentoDTO newInvestimentoDTO = InvestimentoDTO.fromInvestimento(newInvestimento);
            String json = objectMapper.writeValueAsString(newInvestimentoDTO);
            System.out.println("Vamos mandar essa grana pra bolsa então");
            System.out.println(json);
            kafkaTemplate.send(confirmTopic, json);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @KafkaListener(topics = finalizeTopic, groupId = group)
    public void bolsaConfirmaçãoListener(String message) {
        try {
            InvestimentoDTO investimentoDTO = objectMapper.readValue(message, InvestimentoDTO.class);
            Conta conta = contaService.findById(investimentoDTO.contaOrigemId());

            Investimento investimento = investimentoDTO.toInvestimento(conta);
            BigDecimal resultado = conta.getSaldo().subtract(investimento.getValue());
            System.out.println("Novo saldo da conta: R$" + resultado);

            conta.setSaldo(resultado);
            investimento.setStatusTransacao(StatusTransacao.FINALIZADA);

            contaService.save(ContaDTO.fromConta(conta));
            investimentoRepository.save(investimento);

            System.out.println("Parabens, você vai ficar rico");
            
        } catch (Exception e) {
            throw new RuntimeException("Erro no fluxo de messageria", e);
        }
        
    }

    public List<Investimento> findAll() {
        List<Investimento> investimentos;
        
        investimentos = new ArrayList<>();
        investimentos = investimentoRepository.findAll();

        return investimentos;
    }

    public Investimento findById(long id) {
        Optional<Investimento> existingInvestimento = investimentoRepository.findById(id);
        
        if (existingInvestimento.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
        }

        return existingInvestimento.get();

    }

    public Investimento save(Investimento investimento) {
        Investimento newInvestimento = investimentoRepository.save(investimento);
        return newInvestimento;
    }

    public Investimento update(Investimento investimento){

        Investimento updatedInvestimento = null;

        Investimento oldInvestimento = findById(investimento.getId());
        EntityMapper.update(oldInvestimento, investimento);
        updatedInvestimento = investimentoRepository.save(oldInvestimento);

        return updatedInvestimento;
    }

    public void delete(long id) {
        investimentoRepository.deleteById(id);
    }
}
