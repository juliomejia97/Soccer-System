/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "ENTRADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entrada.findAll", query = "SELECT e FROM Entrada e")
    , @NamedQuery(name = "Entrada.findByIdentrada", query = "SELECT e FROM Entrada e WHERE e.identrada = :identrada")})
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDENTRADA")
    private Integer identrada;
    @JoinColumn(name = "IDFASE", referencedColumnName = "IDFASE")
    @ManyToOne
    private Fase idfase;
    @JoinColumns({
        @JoinColumn(name = "IDSILLA", referencedColumnName = "IDSILLA")
        , @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO")})
    @ManyToOne
    private Silla silla;
    @JoinColumn(name = "IDTIQUETE", referencedColumnName = "IDTIQUETE")
    @ManyToOne
    private Tiquete idtiquete;

    public Entrada() {
    }

    public Entrada(Integer identrada) {
        this.identrada = identrada;
    }

    public Integer getIdentrada() {
        return identrada;
    }

    public void setIdentrada(Integer identrada) {
        this.identrada = identrada;
    }

    public Fase getIdfase() {
        return idfase;
    }

    public void setIdfase(Fase idfase) {
        this.idfase = idfase;
    }

    public Silla getSilla() {
        return silla;
    }

    public void setSilla(Silla silla) {
        this.silla = silla;
    }

    public Tiquete getIdtiquete() {
        return idtiquete;
    }

    public void setIdtiquete(Tiquete idtiquete) {
        this.idtiquete = idtiquete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identrada != null ? identrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrada)) {
            return false;
        }
        Entrada other = (Entrada) object;
        if ((this.identrada == null && other.identrada != null) || (this.identrada != null && !this.identrada.equals(other.identrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Entrada[ identrada=" + identrada + " ]";
    }
    
}
