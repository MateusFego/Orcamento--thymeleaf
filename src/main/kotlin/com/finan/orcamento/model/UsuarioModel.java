package com.finan.orcamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="usuario")
public class UsuarioModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(name="nome_usuario")
    private String nomeUsuario;

    @NotBlank @Column(name="rg_usuario")
    private String rgUsuario;

    @NotBlank @Column(name="cpf_usuario")
    private String cpfUsuario;

    @NotBlank @Column(name="nomemae_usuario")
    private String nomeMaeUsuario;

    public UsuarioModel() {}

    // Getters and Setters (manter os existentes)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getRgUsuario() {return rgUsuario;}
    public void setRgUsuario(String rgUsuario) {this.rgUsuario = rgUsuario;}
    public String getCpfUsuario() {return cpfUsuario;}
    public void setCpfUsuario(String cpfUsuario) {this.cpfUsuario = cpfUsuario;}
    public String getNomeMaeUsuario() {return nomeMaeUsuario;}
    public void setNomeMaeUsuario(String nomeMaeUsuario) {this.nomeMaeUsuario = nomeMaeUsuario;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioModel that = (UsuarioModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}