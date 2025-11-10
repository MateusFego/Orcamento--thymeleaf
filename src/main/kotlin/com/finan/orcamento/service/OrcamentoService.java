package com.finan.orcamento.service;

import com.finan.orcamento.model.OrcamentoModel;
import com.finan.orcamento.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {
    @Autowired
    private OrcamentoRepository orcamentoRepository;

    public List<OrcamentoModel> buscarCadastro(){
        return orcamentoRepository.findAll();
    }

    public OrcamentoModel buscaId(Long id){
        Optional<OrcamentoModel>obj= orcamentoRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new RuntimeException("Orçamento não encontrado");
        }
    }

    public OrcamentoModel cadastrarOrcamento(OrcamentoModel orcamentoModel){
        // VALIDAÇÃO CORRIGIDA PARA POLIMORFISMO
        if (orcamentoModel.getDestinatarioId() == null || orcamentoModel.getDestinatarioTipo() == null) {
            throw new IllegalArgumentException("Um orçamento deve ser associado a um destinatário (Cliente ou Usuário).");
        }

        // Se o responsável não foi setado, assume um padrão (necessário se o campo for NOT NULL no DB)
        if (orcamentoModel.getUsuarioResponsavelId() == null) {
            orcamentoModel.setUsuarioResponsavelId(1L);
        }

        orcamentoModel.calcularIcms();
        return orcamentoRepository.save(orcamentoModel);
    }

    public OrcamentoModel atualizaCadastro(OrcamentoModel orcamentoModel, Long id){
        OrcamentoModel newOrcamentoModel = buscaId(id);
        newOrcamentoModel.setValorOrcamento(orcamentoModel.getValorOrcamento());
        newOrcamentoModel.setIcmsEstados(orcamentoModel.getIcmsEstados());

        // Atualiza campos de polimorfismo na edição
        newOrcamentoModel.setDestinatarioId(orcamentoModel.getDestinatarioId());
        newOrcamentoModel.setDestinatarioTipo(orcamentoModel.getDestinatarioTipo());

        newOrcamentoModel.calcularIcms();
        return orcamentoRepository.save(newOrcamentoModel);
    }

    public void deletaOrcamento(Long id){
        orcamentoRepository.deleteById(id);
    }
}