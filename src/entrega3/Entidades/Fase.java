/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "FASE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fase.findAll", query = "SELECT f FROM Fase f")
    , @NamedQuery(name = "Fase.findByIdfase", query = "SELECT f FROM Fase f WHERE f.idfase = :idfase")
    , @NamedQuery(name = "Fase.findByNombrefase", query = "SELECT f FROM Fase f WHERE f.nombrefase = :nombrefase")})
public class Fase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDFASE")
    private Integer idfase;
    @Column(name = "NOMBREFASE")
    private String nombrefase;
    @OneToMany(mappedBy = "idfase")
    private Collection<Partido> partidoCollection;
    @OneToMany(mappedBy = "idfase")
    private Collection<Entrada> entradaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fase")
    private Collection<Precio> precioCollection;

    public Fase() {
    }

    public Fase(Integer idfase) {
        this.idfase = idfase;
    }

    public Integer getIdfase() {
        return idfase;
    }

    public void setIdfase(Integer idfase) {
        this.idfase = idfase;
    }

    public String getNombrefase() {
        return nombrefase;
    }

    public void setNombrefase(String nombrefase) {
        this.nombrefase = nombrefase;
    }

    @XmlTransient
    public Collection<Partido> getPartidoCollection() {
        return partidoCollection;
    }

    public void setPartidoCollection(Collection<Partido> partidoCollection) {
        this.partidoCollection = partidoCollection;
    }

    @XmlTransient
    public Collection<Entrada> getEntradaCollection() {
        return entradaCollection;
    }

    public void setEntradaCollection(Collection<Entrada> entradaCollection) {
        this.entradaCollection = entradaCollection;
    }

    @XmlTransient
    public Collection<Precio> getPrecioCollection() {
        return precioCollection;
    }

    public void setPrecioCollection(Collection<Precio> precioCollection) {
        this.precioCollection = precioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfase != null ? idfase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fase)) {
            return false;
        }
        Fase other = (Fase) object;
        if ((this.idfase == null && other.idfase != null) || (this.idfase != null && !this.idfase.equals(other.idfase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Fase[ idfase=" + idfase + " ]";
    }
    
}
