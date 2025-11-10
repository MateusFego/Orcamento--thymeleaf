package com.finan.orcamento.controller;

import com.finan.orcamento.model.UsuarioModel;
import com.finan.orcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Collections;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota inicial que carrega a página e a lista de usuários
    @GetMapping
    public String getUsuarioPage(Model model) {
        model.addAttribute("usuarioModel", new UsuarioModel());
        List<UsuarioModel> usuarios = usuarioService.buscarUsuario();
        model.addAttribute("usuarios", usuarios);
        return "usuarioPage";
    }

    // Processa a submissão do formulário para salvar ou atualizar
    @PostMapping
    public String salvarOuAtualizarUsuario(@ModelAttribute @Valid UsuarioModel usuarioModel) {
        if (usuarioModel.getId() == null) {
            usuarioService.cadastrarUsuario(usuarioModel);
        } else {
            usuarioService.atualizarUsuario(usuarioModel.getId(), usuarioModel);
        }
        return "redirect:/usuarios";
    }

    // Carrega a página para edição
    @GetMapping("/{id}")
    public String getUsuarioParaEdicao(@PathVariable("id") Long id, Model model) {
        UsuarioModel usuario = usuarioService.buscaId(id);
        model.addAttribute("usuarioModel", usuario);
        List<UsuarioModel> usuarios = usuarioService.buscarUsuario();
        model.addAttribute("usuarios", usuarios);
        return "usuarioPage";
    }

    // Rota de busca por nome que retorna uma lista de usuários
    @GetMapping("/buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<UsuarioModel>> buscarPorNome(@RequestParam("nome") String nome) {
        List<UsuarioModel> usuariosEncontrados = usuarioService.buscarPorNomeCompleto(nome);
        if (!usuariosEncontrados.isEmpty()) {
            return ResponseEntity.ok(usuariosEncontrados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}