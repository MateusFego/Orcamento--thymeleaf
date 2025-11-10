package com.finan.orcamento.model;

import com.finan.orcamento.model.enums.IcmsEstados;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="orcamento")
public class OrcamentoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IcmsEstados icmsEstados;

    @NotNull
    @Column(name="valor_orcamento")
    private BigDecimal valorOrcamento;

    @Column(name="valor_icms")
    private BigDecimal valorICMS;

    // CAMPOS PARA O POLIMORFISMO DE ASSOCIAÇÃO
    @Column(name="destinatario_id")
    private Long destinatarioId;

    @Column(name="destinatario_tipo")
    private String destinatarioTipo; // "CLIENTE" ou "USUARIO"

    @Column(name="usuario_responsavel_id")
    private Long usuarioResponsavelId; // Quem criou o orçamento

    public void calcularIcms() {
        if (this.icmsEstados != null && this.valorOrcamento != null) {
            this.valorICMS = this.icmsEstados.getStrategy().calcular(this.valorOrcamento);
        } else {
            this.valorICMS = BigDecimal.ZERO;
        }
    }

    public OrcamentoModel(){}

    // Getters and Setters para os novos campos
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getDestinatarioTipo() { return destinatarioTipo; }
    public void setDestinatarioTipo(String destinatarioTipo) { this.destinatarioTipo = destinatarioTipo; }
    public Long getUsuarioResponsavelId() { return usuarioResponsavelId; }
    public void setUsuarioResponsavelId(Long usuarioResponsavelId) { this.usuarioResponsavelId = usuarioResponsavelId; }

    // Getters and Setters restantes
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public IcmsEstados getIcmsEstados() { return icmsEstados; }
    public void setIcmsEstados(IcmsEstados icmsEstados) { this.icmsEstados = icmsEstados; }
    @NotNull public BigDecimal getValorOrcamento() { return valorOrcamento; }
    public void setValorOrcamento(@NotNull BigDecimal valorOrcamento) { this.valorOrcamento = valorOrcamento; }
    public BigDecimal getValorICMS() { return valorICMS; }
    public void setValorICMS(BigDecimal valorICMS) { this.valorICMS = valorICMS; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrcamentoModel that = (OrcamentoModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}