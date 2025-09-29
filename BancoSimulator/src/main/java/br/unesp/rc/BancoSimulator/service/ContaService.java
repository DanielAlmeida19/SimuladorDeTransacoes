package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.ContaRepository;

@Service
public class ContaService {


    @Autowired
    ContaRepository contaRepository;

    public List<Conta> findAll() {
        List<Conta> contas;
        
        contas = new ArrayList<>();
        contas = contaRepository.findAll();

        return contas;
    }

    public Conta findById(long id) {
        Optional<Conta> existingConta = contaRepository.findById(id);
        
        if (existingConta.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
            }

        return existingConta.get();

    }

    public Conta save(Conta conta) {
        Conta newConta = contaRepository.save(conta);
        return newConta;
    }

    public Conta update(Conta conta){

        Conta updatedConta = null;

        Conta oldConta = findById(conta.getId());
        EntityMapper.update(oldConta, conta);
        updatedConta = contaRepository.save(oldConta);

        return updatedConta;
    }

    public void delete(long id) {
        contaRepository.deleteById(id);
    }
}
