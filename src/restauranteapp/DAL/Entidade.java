/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restauranteapp.DAL;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kevin
 */


@Entity
@Table(name= "entidade")
public class Entidade implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id_entidade")
    private Integer id_entidade;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nif")
    private Integer NIF;
    @Column(name = "rua")
    private String rua;
    @Column(name = "nporta")
    private Integer nPorta;
    @Column(name = "codpostal")
    private String cod_postal;
    @Column(name = "telefone")
    private Integer Telefone;
    @Column(name = "passwordp")
    private String password;
    @Column(name = "nivelpermissao")
    private Integer nivelPermissao;

    
    
    
    public Integer getId_entidade() {
        return id_entidade;
    }

    public void setId_entidade(Integer id_entidade) {
        this.id_entidade = id_entidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNIF() {
        return NIF;
    }

    public void setNIF(Integer NIF) {
        this.NIF = NIF;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getnPorta() {
        return nPorta;
    }

    public void setnPorta(Integer nPorta) {
        this.nPorta = nPorta;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public Integer getTelefone() {
        return Telefone;
    }

    public void setTelefone(Integer Telefone) {
        this.Telefone = Telefone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNivelPermissao() {
        return nivelPermissao;
    }

    public void setNivelPermissao(Integer nivelPermissao) {
        this.nivelPermissao = nivelPermissao;
    }
    
    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (id_entidade != null ? id_entidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidade)) {
            return false;
        }
        Entidade other = (Entidade) object;
        if ((this.id_entidade == null && other.id_entidade != null) || (this.id_entidade != null && !this.id_entidade.equals(other.id_entidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restauranteapp.DAL.Entidade[ id_entidade=" + id_entidade + " ]";
    }
    
    
    
    
}
