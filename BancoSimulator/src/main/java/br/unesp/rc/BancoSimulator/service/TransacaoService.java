package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BancoSimulator.model.Transacao;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    TransacaoRepository transacaoRepository;

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
