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

import br.unesp.rc.BancoSimulator.model.Conta;
import br.unesp.rc.BancoSimulator.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    ContaService contaService;

    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<?> findAll() {
        try {
            List<Conta> contas = contaService.findAll();

            // Caso de sucesso
            return new ResponseEntity<List<Conta>>(
                contas,
                HttpStatus.OK
            );
        } catch (Exception e) {
            System.out.println("Erro ao retornar contas: " + e);

            return new ResponseEntity<Error>(
                new Error("Erro ao retornar contas", e),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getById(@PathVariable(value = "id") long id) {
        try {
            Conta conta = contaService.findById(id);

            // Caso de sucesso
            return new ResponseEntity<Conta>(
                conta, HttpStatus.OK
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
    public ResponseEntity<?> save(@RequestBody Conta conta) {
        try {
            Conta newConta = contaService.save(conta);

            // Caso de sucesso
            return new ResponseEntity<Conta>(
                newConta, 
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
    public ResponseEntity<?> update(@RequestBody Conta conta) {
        try {
            Conta updatedConta = contaService.update(conta);

            // Caso de sucesso
            return new ResponseEntity<Conta>(
                updatedConta, 
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
            contaService.delete(id);

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
