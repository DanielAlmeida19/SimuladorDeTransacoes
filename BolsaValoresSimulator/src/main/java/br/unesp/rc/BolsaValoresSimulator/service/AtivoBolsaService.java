package br.unesp.rc.BolsaValoresSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BolsaValoresSimulator.model.AtivoBolsa;
import br.unesp.rc.BolsaValoresSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BolsaValoresSimulator.repository.AtivoBolsaRepository;

@Service
public class AtivoBolsaService {


    @Autowired
    AtivoBolsaRepository ativoBolsaRepository;

    public List<AtivoBolsa> findAll() {
        List<AtivoBolsa> ativoBolsas;

        ativoBolsas = new ArrayList<>();
        ativoBolsas = ativoBolsaRepository.findAll();

        return ativoBolsas;
    }

    public AtivoBolsa findById(long id) {
        Optional<AtivoBolsa> existingAtivoBolsa = ativoBolsaRepository.findById(id);

        if (existingAtivoBolsa.isEmpty()) {
                throw new NoSuchElementException("Não encontrado ativo com ID: " + id);
        }

        return existingAtivoBolsa.get();

    }

    public AtivoBolsa save(AtivoBolsa ativoBolsa) {
        AtivoBolsa newAtivoBolsa = ativoBolsaRepository.save(ativoBolsa);
        return newAtivoBolsa;
    }

    public AtivoBolsa update(AtivoBolsa ativoBolsa){

        AtivoBolsa updatedAtivoBolsa = null;

        AtivoBolsa oldAtivoBolsa = findById(ativoBolsa.getId());
        EntityMapper.update(oldAtivoBolsa, ativoBolsa);
        updatedAtivoBolsa = ativoBolsaRepository.save(oldAtivoBolsa);

        return updatedAtivoBolsa;
    }

    public void delete(long id) {
        ativoBolsaRepository.deleteById(id);
    }
}

