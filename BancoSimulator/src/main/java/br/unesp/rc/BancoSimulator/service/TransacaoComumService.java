package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BancoSimulator.model.TransacaoComum;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.TransacaoComumRepository;

@Service
public class TransacaoComumService {


    @Autowired
    TransacaoComumRepository transacaoRepository;

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
