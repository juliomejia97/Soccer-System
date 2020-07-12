/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "BANCO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b")
    , @NamedQuery(name = "Banco.findByIdbanco", query = "SELECT b FROM Banco b WHERE b.idbanco = :idbanco")
    , @NamedQuery(name = "Banco.findByNombrebanco", query = "SELECT b FROM Banco b WHERE b.nombrebanco = :nombrebanco")})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDBANCO")
    private Integer idbanco;
    @Column(name = "NOMBREBANCO")
    private String nombrebanco;
    @OneToMany(mappedBy = "idbanco")
    private Collection<Tarjetacredito> tarjetacreditoCollection;

    public Banco() {
    }

    public Banco(Integer idbanco) {
        this.idbanco = idbanco;
    }

    public Integer getIdbanco() {
        return idbanco;
    }

    public void setIdbanco(Integer idbanco) {
        this.idbanco = idbanco;
    }

    public String getNombrebanco() {
        return nombrebanco;
    }

    public void setNombrebanco(String nombrebanco) {
        this.nombrebanco = nombrebanco;
    }

    @XmlTransient
    public Collection<Tarjetacredito> getTarjetacreditoCollection() {
        return tarjetacreditoCollection;
    }

    public void setTarjetacreditoCollection(Collection<Tarjetacredito> tarjetacreditoCollection) {
        this.tarjetacreditoCollection = tarjetacreditoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbanco != null ? idbanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.idbanco == null && other.idbanco != null) || (this.idbanco != null && !this.idbanco.equals(other.idbanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Banco[ idbanco=" + idbanco + " ]";
    }
    
}
