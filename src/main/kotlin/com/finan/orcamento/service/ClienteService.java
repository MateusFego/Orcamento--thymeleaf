package com.finan.orcamento.service;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public List<ClienteModel> buscarCliente(){
        return clienteRepository.findAll();
    }

    @Transactional
    public ClienteModel buscaId(Long id){
        Optional<ClienteModel> obj = clienteRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new RuntimeException("Cliente não encontrado");
        }
    }

    public ClienteModel cadastrarCliente(ClienteModel clienteModel){
        return clienteRepository.save(clienteModel);
    }

    @Transactional
    public List<ClienteModel> buscarPorNomeCompleto(String nome) {
        return clienteRepository.findByNomeClienteContaining(nome);
    }

    public ClienteModel atualizarCliente(Long id, ClienteModel clienteAtualizado) {
        ClienteModel clienteExistente = buscaId(id);
        clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
        clienteExistente.setCpfCliente(clienteAtualizado.getCpfCliente());
        return clienteRepository.save(clienteExistente);
    }

    public void deletaCliente(Long id){
        clienteRepository.deleteById(id);
    }
}