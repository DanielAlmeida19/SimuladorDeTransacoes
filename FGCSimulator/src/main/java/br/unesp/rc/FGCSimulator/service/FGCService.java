package br.unesp.rc.FGCSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.FGCSimulator.model.FGC;
import br.unesp.rc.FGCSimulator.model.mapper.EntityMapper;
import br.unesp.rc.FGCSimulator.repository.FGCRepository;

@Service
public class FGCService {


    @Autowired
    FGCRepository fgcRepository;

    public List<FGC> findAll() {
        List<FGC> fgcs;

        fgcs = new ArrayList<>();
        fgcs = fgcRepository.findAll();

        return fgcs;
    }

    public FGC findById(long id) {
        Optional<FGC> existingFGC = fgcRepository.findById(id);

        if (existingFGC.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
            }

        return existingFGC.get();

    }

    public FGC save(FGC fgc) {
        FGC newFGC = fgcRepository.save(fgc);
        return newFGC;
    }

    public FGC update(FGC fgc){

        FGC updatedFGC = null;

        FGC oldFGC = findById(fgc.getId());
        EntityMapper.update(oldFGC, fgc);
        updatedFGC = fgcRepository.save(oldFGC);

        return updatedFGC;
    }

    public void delete(long id) {
        fgcRepository.deleteById(id);
    }
}

