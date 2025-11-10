package com.finan.orcamento.service;

import com.finan.orcamento.model.UsuarioModel;
import com.finan.orcamento.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<UsuarioModel> buscarUsuario(){
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        // Chamadas a getOrcamentos() removidas
        return usuarios;
    }

    @Transactional
    public UsuarioModel buscaId(Long id){
        Optional<UsuarioModel> obj=usuarioRepository.findById(id);
        if (obj.isPresent()) {
            UsuarioModel usuario = obj.get();
            return usuario;
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public UsuarioModel cadastrarUsuario(UsuarioModel usuarioModel){
        return usuarioRepository.save(usuarioModel);
    }

    @Transactional
    public List<UsuarioModel> buscarPorNomeCompleto(String nome) {
        List<UsuarioModel> usuarios = usuarioRepository.findByNomeUsuarioContaining(nome);
        return usuarios;
    }

    public UsuarioModel atualizarUsuario(Long id, UsuarioModel usuarioAtualizado) {
        UsuarioModel usuarioExistente = buscaId(id);
        usuarioExistente.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
        usuarioExistente.setCpfUsuario(usuarioAtualizado.getCpfUsuario());
        usuarioExistente.setRgUsuario(usuarioAtualizado.getRgUsuario());
        usuarioExistente.setNomeMaeUsuario(usuarioAtualizado.getNomeMaeUsuario());
        return usuarioRepository.save(usuarioExistente);
    }

    public void deletaUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}