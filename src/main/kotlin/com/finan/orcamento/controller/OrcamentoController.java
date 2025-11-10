package com.finan.orcamento.controller;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.model.OrcamentoModel;
import com.finan.orcamento.model.UsuarioModel;
import com.finan.orcamento.service.ClienteService;
import com.finan.orcamento.service.OrcamentoService;
import com.finan.orcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/orcamentos")
public class OrcamentoController {

    @Autowired private OrcamentoService orcamentoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private ClienteService clienteService;

    @GetMapping("/tela")
    public String telaOrcamentos() {
        return "orcamentos";
    }

    // --- ROTA PARA BUSCA UNIFICADA (Resolve o problema da tela) ---
    @GetMapping("/buscarDestinatario")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> buscarDestinatario(@RequestParam("nome") String nome) {

        List<Map<String, Object>> resultados = new ArrayList<>();

        // 1. Busca Clientes e formata
        List<ClienteModel> clientes = clienteService.buscarPorNomeCompleto(nome);
        clientes.forEach(c -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", c.getId());
            item.put("nome", c.getNomeCliente());
            item.put("tipo", "CLIENTE");
            item.put("identificador", "CPF: " + c.getCpfCliente());
            resultados.add(item);
        });

        // 2. Busca Usuários e formata
        List<UsuarioModel> usuarios = usuarioService.buscarPorNomeCompleto(nome);
        usuarios.forEach(u -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", u.getId());
            item.put("nome", u.getNomeUsuario());
            item.put("tipo", "USUARIO");
            item.put("identificador", "RG: " + u.getRgUsuario());
            resultados.add(item);
        });

        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }
    // --------------------------------------------------------

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<OrcamentoModel>> buscaTodosOrcamentos() {
        return ResponseEntity.ok(orcamentoService.buscarCadastro());
    }

    @GetMapping(path="/pesquisaid/{id}")
    @ResponseBody
    public ResponseEntity<OrcamentoModel> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(orcamentoService.buscaId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<OrcamentoModel> cadastraOrcamento(@RequestBody OrcamentoModel orcamentoModel) {
        return ResponseEntity.ok(orcamentoService.cadastrarOrcamento(orcamentoModel));
    }

    @PostMapping(path="/put/{id}")
    @ResponseBody
    public ResponseEntity<OrcamentoModel> atualizaOrcamento(@RequestBody OrcamentoModel orcamentoModel, @PathVariable Long id) {
        OrcamentoModel orcamentoNewObj = orcamentoService.atualizaCadastro(orcamentoModel, id);
        return ResponseEntity.ok().body(orcamentoNewObj);
    }

    @DeleteMapping(path="/delete/{id}")
    @ResponseBody
    public void deleteOrcamento(@PathVariable Long id) {
        orcamentoService.deletaOrcamento(id);
    }

    // Rota obsoleta mantida com No Content para evitar erros no frontend, caso ainda a chame
    @GetMapping("/buscarPorNomeUsuario")
    @ResponseBody
    public ResponseEntity<List<OrcamentoModel>> buscaOrcamentosPorNomeUsuario(@RequestParam("nome") String nome) {
        return ResponseEntity.noContent().build();
    }
}