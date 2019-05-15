/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Medeiros
 */
@Entity
@Table(name = "lote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lote.findAll", query = "SELECT l FROM Lote l"),
    @NamedQuery(name = "Lote.findByIdLote", query = "SELECT l FROM Lote l WHERE l.idLote = :idLote"),
    @NamedQuery(name = "Lote.findByNumeroLote", query = "SELECT l FROM Lote l WHERE l.numeroLote = :numeroLote"),
    @NamedQuery(name = "Lote.findByDataValidade", query = "SELECT l FROM Lote l WHERE l.dataValidade = :dataValidade"),
    @NamedQuery(name = "Lote.findByDataEntrada", query = "SELECT l FROM Lote l WHERE l.dataEntrada = :dataEntrada"),
    @NamedQuery(name = "Lote.findByQuantidade", query = "SELECT l FROM Lote l WHERE l.quantidade = :quantidade"),
    @NamedQuery(name = "Lote.findByDataModificacao", query = "SELECT l FROM Lote l WHERE l.dataModificacao = :dataModificacao"),
    @NamedQuery(name = "Lote.findByUsrModificacao", query = "SELECT l FROM Lote l WHERE l.usrModificacao = :usrModificacao"),
    @NamedQuery(name = "Lote.findByJustificativaEdit", query = "SELECT l FROM Lote l WHERE l.justificativaEdit = :justificativaEdit"),
    @NamedQuery(name = "Lote.findByEntradaInicial", query = "SELECT l FROM Lote l WHERE l.entradaInicial = :entradaInicial")})
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lote")
    private Integer idLote;
    @Column(name = "numero_lote")
    private String numeroLote;
    @Column(name = "data_validade")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Basic(optional = false)
    @Column(name = "data_entrada")
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "data_modificacao")
    @Temporal(TemporalType.DATE)
    private Date dataModificacao;
    @Column(name = "usr_modificacao")
    private Integer usrModificacao;
    @Column(name = "justificativa_edit")
    private String justificativaEdit;
    @Basic(optional = false)
    @Column(name = "entradaInicial")
    private int entradaInicial;
    @JoinColumn(name = "prod", referencedColumnName = "id_produto")
    @ManyToOne
    private Produto prod;
    @OneToMany(mappedBy = "lot")
    private Collection<Pedido> pedidoCollection;

    public Lote() {
    }

    public Lote(Integer idLote) {
        this.idLote = idLote;
    }

    public Lote(Integer idLote, Date dataEntrada, int entradaInicial) {
        this.idLote = idLote;
        this.dataEntrada = dataEntrada;
        this.entradaInicial = entradaInicial;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Integer getUsrModificacao() {
        return usrModificacao;
    }

    public void setUsrModificacao(Integer usrModificacao) {
        this.usrModificacao = usrModificacao;
    }

    public String getJustificativaEdit() {
        return justificativaEdit;
    }

    public void setJustificativaEdit(String justificativaEdit) {
        this.justificativaEdit = justificativaEdit;
    }

    public int getEntradaInicial() {
        return entradaInicial;
    }

    public void setEntradaInicial(int entradaInicial) {
        this.entradaInicial = entradaInicial;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLote != null ? idLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.idLote == null && other.idLote != null) || (this.idLote != null && !this.idLote.equals(other.idLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Lote[ idLote=" + idLote + " ]";
    }
    
}
