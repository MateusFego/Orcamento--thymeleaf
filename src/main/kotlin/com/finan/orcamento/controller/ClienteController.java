package com.finan.orcamento.controller;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String getClientePage(Model model) {
        model.addAttribute("clienteModel", new ClienteModel());
        List<ClienteModel> clientes = clienteService.buscarCliente();
        model.addAttribute("clientes", clientes);
        return "clientePage";
    }

    @PostMapping
    public String salvarOuAtualizarCliente(@ModelAttribute @Valid ClienteModel clienteModel) {
        if (clienteModel.getId() == null) {
            clienteService.cadastrarCliente(clienteModel);
        } else {
            clienteService.atualizarCliente(clienteModel.getId(), clienteModel);
        }
        return "redirect:/clientes";
    }

    @GetMapping("/{id}")
    public String getClienteParaEdicao(@PathVariable("id") Long id, Model model) {
        ClienteModel cliente = clienteService.buscaId(id);
        model.addAttribute("clienteModel", cliente);
        List<ClienteModel> clientes = clienteService.buscarCliente();
        model.addAttribute("clientes", clientes);
        return "clientePage";
    }

    // Rota de exclusão (Para Thymeleaf)
    @GetMapping("/delete/{id}")
    public String deletaCliente(@PathVariable("id") Long id) {
        clienteService.deletaCliente(id);
        return "redirect:/clientes";
    }

    @GetMapping("/buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<ClienteModel>> buscarPorNome(@RequestParam("nome") String nome) {
        List<ClienteModel> clientesEncontrados = clienteService.buscarPorNomeCompleto(nome);
        if (!clientesEncontrados.isEmpty()) {
            return ResponseEntity.ok(clientesEncontrados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}