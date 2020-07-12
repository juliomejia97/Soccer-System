/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "SILLA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Silla.findAll", query = "SELECT s FROM Silla s")
    , @NamedQuery(name = "Silla.findByIdsilla", query = "SELECT s FROM Silla s WHERE s.sillaPK.idsilla = :idsilla")
    , @NamedQuery(name = "Silla.findByIdestadio", query = "SELECT s FROM Silla s WHERE s.sillaPK.idestadio = :idestadio")
    , @NamedQuery(name = "Silla.findByOcupado", query = "SELECT s FROM Silla s WHERE s.ocupado = :ocupado")})
public class Silla implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SillaPK sillaPK;
    @Column(name = "OCUPADO")
    private Short ocupado;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA")
    @ManyToOne
    private Categoria idcategoria;
    @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estadio estadio;
    @OneToMany(mappedBy = "silla")
    private Collection<Entrada> entradaCollection;

    public Silla() {
    }

    public Silla(SillaPK sillaPK) {
        this.sillaPK = sillaPK;
    }

    public Silla(int idsilla, int idestadio) {
        this.sillaPK = new SillaPK(idsilla, idestadio);
    }

    public SillaPK getSillaPK() {
        return sillaPK;
    }

    public void setSillaPK(SillaPK sillaPK) {
        this.sillaPK = sillaPK;
    }

    public Short getOcupado() {
        return ocupado;
    }

    public void setOcupado(Short ocupado) {
        this.ocupado = ocupado;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    @XmlTransient
    public Collection<Entrada> getEntradaCollection() {
        return entradaCollection;
    }

    public void setEntradaCollection(Collection<Entrada> entradaCollection) {
        this.entradaCollection = entradaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sillaPK != null ? sillaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Silla)) {
            return false;
        }
        Silla other = (Silla) object;
        if ((this.sillaPK == null && other.sillaPK != null) || (this.sillaPK != null && !this.sillaPK.equals(other.sillaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Silla[ sillaPK=" + sillaPK + " ]";
    }
    
}
