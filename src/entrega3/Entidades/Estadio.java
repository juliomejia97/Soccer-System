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
@Table(name = "ESTADIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadio.findAll", query = "SELECT e FROM Estadio e")
    , @NamedQuery(name = "Estadio.findByIdestadio", query = "SELECT e FROM Estadio e WHERE e.idestadio = :idestadio")
    , @NamedQuery(name = "Estadio.findByNombreestadio", query = "SELECT e FROM Estadio e WHERE e.nombreestadio = :nombreestadio")})
public class Estadio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private Integer idestadio;
    @Column(name = "NOMBREESTADIO")
    private String nombreestadio;
    @JoinColumn(name = "IDCIUDAD", referencedColumnName = "IDCIUDAD")
    @ManyToOne
    private Ciudad idciudad;
    @OneToMany(mappedBy = "idestadio")
    private Collection<Partido> partidoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadio")
    private Collection<Silla> sillaCollection;

    public Estadio() {
    }

    public Estadio(Integer idestadio) {
        this.idestadio = idestadio;
    }

    public Integer getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(Integer idestadio) {
        this.idestadio = idestadio;
    }

    public String getNombreestadio() {
        return nombreestadio;
    }

    public void setNombreestadio(String nombreestadio) {
        this.nombreestadio = nombreestadio;
    }

    public Ciudad getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(Ciudad idciudad) {
        this.idciudad = idciudad;
    }

    @XmlTransient
    public Collection<Partido> getPartidoCollection() {
        return partidoCollection;
    }

    public void setPartidoCollection(Collection<Partido> partidoCollection) {
        this.partidoCollection = partidoCollection;
    }

    @XmlTransient
    public Collection<Silla> getSillaCollection() {
        return sillaCollection;
    }

    public void setSillaCollection(Collection<Silla> sillaCollection) {
        this.sillaCollection = sillaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadio != null ? idestadio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadio)) {
            return false;
        }
        Estadio other = (Estadio) object;
        if ((this.idestadio == null && other.idestadio != null) || (this.idestadio != null && !this.idestadio.equals(other.idestadio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Estadio[ idestadio=" + idestadio + " ]";
    }
    
}
