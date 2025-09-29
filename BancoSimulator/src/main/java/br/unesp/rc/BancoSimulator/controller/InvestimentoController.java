package br.unesp.rc.BancoSimulator.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.BancoSimulator.model.Investimento;
import br.unesp.rc.BancoSimulator.service.InvestimentoService;

@RestController
@RequestMapping("/investimento")
public class InvestimentoController {

    @Autowired
    InvestimentoService investimentoService;

    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<?> findAll() {
        try {
            List<Investimento> investimentos = investimentoService.findAll();

            // Caso de sucesso
            return new ResponseEntity<List<Investimento>>(
                investimentos,
                HttpStatus.OK
            );
        } catch (Exception e) {
            System.out.println("Erro ao retornar investimentos: " + e);

            return new ResponseEntity<Error>(
                new Error("Erro ao retornar investimentos", e),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getById(@PathVariable(value = "id") long id) {
        try {
            Investimento investimento = investimentoService.findById(id);

            // Caso de sucesso
            return new ResponseEntity<Investimento>(
                investimento, HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            
            // Caso não encontre
            System.out.println(e);
            return new ResponseEntity<Error>(
                new Error("Elemento não encontrado", e),
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<?> save(@RequestBody Investimento investimento) {
        try {
            Investimento newInvestimento = investimentoService.save(investimento);

            // Caso de sucesso
            return new ResponseEntity<Investimento>(
                newInvestimento, 
                HttpStatus.OK
            );
        } catch (Exception e) {
            
            // Caso de fracasso
            System.out.println("Algo deu errado ao salvar o elemento: " + e);
            return new ResponseEntity<Error>(
                new Error(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Investimento investimento) {
        try {
            Investimento updatedInvestimento = investimentoService.update(investimento);

            // Caso de sucesso
            return new ResponseEntity<Investimento>(
                updatedInvestimento, 
                HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            
            // Caso não encontre
            System.out.println(e);
            return new ResponseEntity<Error>(
                new Error("Elemento não encontrado", e), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (Exception e) {

            // Caso de fracasso
            System.out.println("Erro ao atualizar elemento: " + e);
            return new ResponseEntity<Error>(
                new Error("Algo deu errado ao deletar elemento: ", e),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        try {
            investimentoService.delete(id);

            // Caso de sucesso
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            
            // Caso de fracasso
            System.out.println("Erro ao deletar elemento: " + e);
            return new ResponseEntity<Error>(
                new Error("Algo deu errado ao deleter elemento", e),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}
