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
@Table(name = "CATEGORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdcategoria", query = "SELECT c FROM Categoria c WHERE c.idcategoria = :idcategoria")
    , @NamedQuery(name = "Categoria.findByNombrecategoria", query = "SELECT c FROM Categoria c WHERE c.nombrecategoria = :nombrecategoria")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCATEGORIA")
    private Integer idcategoria;
    @Column(name = "NOMBRECATEGORIA")
    private String nombrecategoria;
    @OneToMany(mappedBy = "idcategoria")
    private Collection<Silla> sillaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Precio> precioCollection;

    public Categoria() {
    }

    public Categoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    @XmlTransient
    public Collection<Silla> getSillaCollection() {
        return sillaCollection;
    }

    public void setSillaCollection(Collection<Silla> sillaCollection) {
        this.sillaCollection = sillaCollection;
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
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Categoria[ idcategoria=" + idcategoria + " ]";
    }
    
}
