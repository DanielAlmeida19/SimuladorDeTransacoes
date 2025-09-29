package br.unesp.rc.BancoSimulator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.BancoSimulator.model.Cliente;
import br.unesp.rc.BancoSimulator.model.mapper.EntityMapper;
import br.unesp.rc.BancoSimulator.repository.ClienteRepository;

@Service
public class ClienteService {


    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        List<Cliente> clientes;
        
        clientes = new ArrayList<>();
        clientes = clienteRepository.findAll();

        return clientes;
    }

    public Cliente findById(long id) {
        Optional<Cliente> existingCliente = clienteRepository.findById(id);
        
        if (existingCliente.isEmpty()) {
                throw new NoSuchElementException("Não encontrada transação com ID: " + id);
            }

        return existingCliente.get();

    }

    public Cliente save(Cliente cliente) {
        Cliente newCliente = clienteRepository.save(cliente);
        return newCliente;
    }

    public Cliente update(Cliente cliente){

        Cliente updatedCliente = null;

        Cliente oldCliente = findById(cliente.getId());
        EntityMapper.update(oldCliente, cliente);
        updatedCliente = clienteRepository.save(oldCliente);

        return updatedCliente;
    }

    public void delete(long id) {
        clienteRepository.deleteById(id);
    }
}
