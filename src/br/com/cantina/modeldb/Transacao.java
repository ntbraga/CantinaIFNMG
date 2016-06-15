/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.modeldb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ntbra
 */
@Entity
@Table(name = "TRANSACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transacao.findAll", query = "SELECT t FROM Transacao t"),
    @NamedQuery(name = "Transacao.findByIdtransacao", query = "SELECT t FROM Transacao t WHERE t.idtransacao = :idtransacao"),
    @NamedQuery(name = "Transacao.findByIdcaixa", query = "SELECT t FROM Transacao t WHERE t.idcaixa = :idcaixa"),
    @NamedQuery(name = "Transacao.findByIdcliente", query = "SELECT t FROM Transacao t WHERE t.idcliente = :idcliente"),
    @NamedQuery(name = "Transacao.findByValue", query = "SELECT t FROM Transacao t WHERE t.value = :value"),
    @NamedQuery(name = "Transacao.findByType", query = "SELECT t FROM Transacao t WHERE t.type = :type"),
    @NamedQuery(name = "Transacao.findByTimestamp", query = "SELECT t FROM Transacao t WHERE t.timestamp = :timestamp"),
    @NamedQuery(name = "Transacao.findByIdCaixaAndType", query = "SELECT t FROM Transacao t WHERE t.idcaixa = :idcaixa AND t.type = :type AND t.idcliente IS NOT NULL ORDER BY t.timestamp DESC"),
    @NamedQuery(name = "Transacao.findVendaAVista", query = "SELECT t FROM Transacao t WHERE t.idcaixa = :idcaixa AND t.type = 'venda' AND t.idcliente IS NULL ORDER BY t.timestamp DESC"),
    @NamedQuery(name = "Transacao.findPaymentsByCaixa", query = "SELECT t FROM Transacao t WHERE t.idcaixa = :idcaixa AND ((t.type = 'venda' AND t.idcliente IS NULL) OR (t.type = 'pay' AND t.idcliente IS NOT NULL)) ORDER BY t.timestamp DESC"),
    @NamedQuery(name = "Transacao.findVendasByIdcliente", query = "SELECT t FROM Transacao t WHERE t.idcliente = :idcliente AND t.type = 'venda'"),
    @NamedQuery(name = "Transacao.findPayByIdcliente", query = "SELECT t FROM Transacao t WHERE t.idcliente = :idcliente AND t.type = 'pay'"),
    @NamedQuery(name = "Transacao.deleteAll", query = "DELETE FROM Transacao")})
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTRANSACAO")
    private Integer idtransacao;
    @Basic(optional = false)
    @Column(name = "IDCAIXA")
    private int idcaixa;
    @Column(name = "IDCLIENTE")
    private Integer idcliente;
    @Basic(optional = false)
    @Column(name = "VALUE")
    private double value;
    @Basic(optional = false)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Transacao() {
    }

    public Transacao(Integer idtransacao) {
        this.idtransacao = idtransacao;
    }

    public Transacao(Integer idtransacao, int idcaixa, double value, String type, Date timestamp) {
        this.idtransacao = idtransacao;
        this.idcaixa = idcaixa;
        this.value = value;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Integer getIdtransacao() {
        return idtransacao;
    }

    public void setIdtransacao(Integer idtransacao) {
        this.idtransacao = idtransacao;
    }

    public int getIdcaixa() {
        return idcaixa;
    }

    public void setIdcaixa(int idcaixa) {
        this.idcaixa = idcaixa;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransacao != null ? idtransacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacao)) {
            return false;
        }
        Transacao other = (Transacao) object;
        if ((this.idtransacao == null && other.idtransacao != null) || (this.idtransacao != null && !this.idtransacao.equals(other.idtransacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.cantina.modeldb.Transacao[ idtransacao=" + idtransacao + " ]";
    }
    
}
