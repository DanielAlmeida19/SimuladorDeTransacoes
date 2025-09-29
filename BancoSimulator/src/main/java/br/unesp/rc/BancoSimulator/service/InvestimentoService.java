package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BancoSimulator.model.Investimento;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.InvestimentoRepository;

@Service
public class InvestimentoService {


    @Autowired
    InvestimentoRepository investimentoRepository;

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
