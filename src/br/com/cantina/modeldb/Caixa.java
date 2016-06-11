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
@Table(name = "CAIXA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caixa.findAll", query = "SELECT c FROM Caixa c"),
    @NamedQuery(name = "Caixa.findByIdcaixa", query = "SELECT c FROM Caixa c WHERE c.idcaixa = :idcaixa"),
    @NamedQuery(name = "Caixa.findByAberto", query = "SELECT c FROM Caixa c WHERE c.aberto = :aberto"),
    @NamedQuery(name = "Caixa.findByTimeopen", query = "SELECT c FROM Caixa c WHERE c.timeopen = :timeopen"),
    @NamedQuery(name = "Caixa.findByTimeclose", query = "SELECT c FROM Caixa c WHERE c.timeclose = :timeclose"),
    @NamedQuery(name = "Caixa.findNotClosed", query = "SELECT c FROM Caixa c WHERE c.aberto = true")})
public class Caixa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCAIXA")
    private Integer idcaixa;
    @Basic(optional = false)
    @Column(name = "ABERTO")
    private Boolean aberto;
    @Basic(optional = false)
    @Column(name = "TIMEOPEN")
    @Temporal(TemporalType.DATE)
    private Date timeopen;
    @Column(name = "TIMECLOSE")
    @Temporal(TemporalType.DATE)
    private Date timeclose;

    public Caixa() {
    }

    public Caixa(Integer idcaixa) {
        this.idcaixa = idcaixa;
    }

    public Caixa(Integer idcaixa, Boolean aberto, Date timeopen) {
        this.idcaixa = idcaixa;
        this.aberto = aberto;
        this.timeopen = timeopen;
    }

    public Integer getIdcaixa() {
        return idcaixa;
    }

    public void setIdcaixa(Integer idcaixa) {
        this.idcaixa = idcaixa;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public Date getTimeopen() {
        return timeopen;
    }

    public void setTimeopen(Date timeopen) {
        this.timeopen = timeopen;
    }

    public Date getTimeclose() {
        return timeclose;
    }

    public void setTimeclose(Date timeclose) {
        this.timeclose = timeclose;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcaixa != null ? idcaixa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caixa)) {
            return false;
        }
        Caixa other = (Caixa) object;
        if ((this.idcaixa == null && other.idcaixa != null) || (this.idcaixa != null && !this.idcaixa.equals(other.idcaixa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.cantina.modeldb.Caixa[ idcaixa=" + idcaixa + " ]";
    }
    
}
