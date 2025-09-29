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

import br.unesp.rc.BancoSimulator.model.Cliente;
import br.unesp.rc.BancoSimulator.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<?> findAll() {
        try {
            List<Cliente> clientes = clienteService.findAll();

            // Caso de sucesso
            return new ResponseEntity<List<Cliente>>(
                clientes,
                HttpStatus.OK
            );
        } catch (Exception e) {
            System.out.println("Erro ao retornar clientes: " + e);

            return new ResponseEntity<Error>(
                new Error("Erro ao retornar clientes", e),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getById(@PathVariable(value = "id") long id) {
        try {
            Cliente cliente = clienteService.findById(id);

            // Caso de sucesso
            return new ResponseEntity<Cliente>(
                cliente, HttpStatus.OK
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
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.save(cliente);

            // Caso de sucesso
            return new ResponseEntity<Cliente>(
                newCliente, 
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
    public ResponseEntity<?> update(@RequestBody Cliente cliente) {
        try {
            Cliente updatedCliente = clienteService.update(cliente);

            // Caso de sucesso
            return new ResponseEntity<Cliente>(
                updatedCliente, 
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
            clienteService.delete(id);

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
