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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TARJETACREDITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjetacredito.findAll", query = "SELECT t FROM Tarjetacredito t")
    , @NamedQuery(name = "Tarjetacredito.findByNumerotarjeta", query = "SELECT t FROM Tarjetacredito t WHERE t.numerotarjeta = :numerotarjeta")})
public class Tarjetacredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NUMEROTARJETA")
    private Integer numerotarjeta;
    @OneToMany(mappedBy = "numerotarjeta")
    private Collection<Pago> pagoCollection;
    @JoinColumn(name = "IDBANCO", referencedColumnName = "IDBANCO")
    @ManyToOne
    private Banco idbanco;
    @OneToMany(mappedBy = "numerotarjeta")
    private Collection<Cliente> clienteCollection;

    public Tarjetacredito() {
    }

    public Tarjetacredito(Integer numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public Integer getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(Integer numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    @XmlTransient
    public Collection<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(Collection<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    public Banco getIdbanco() {
        return idbanco;
    }

    public void setIdbanco(Banco idbanco) {
        this.idbanco = idbanco;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerotarjeta != null ? numerotarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjetacredito)) {
            return false;
        }
        Tarjetacredito other = (Tarjetacredito) object;
        if ((this.numerotarjeta == null && other.numerotarjeta != null) || (this.numerotarjeta != null && !this.numerotarjeta.equals(other.numerotarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Tarjetacredito[ numerotarjeta=" + numerotarjeta + " ]";
    }
    
}
