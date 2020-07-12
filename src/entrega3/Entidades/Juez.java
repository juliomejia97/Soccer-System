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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "JUEZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juez.findAll", query = "SELECT j FROM Juez j")
    , @NamedQuery(name = "Juez.findByIdjuez", query = "SELECT j FROM Juez j WHERE j.idjuez = :idjuez")})
public class Juez implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDJUEZ")
    private Integer idjuez;
    @JoinColumn(name = "IDJUEZ", referencedColumnName = "IDPERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Juez() {
    }

    public Juez(Integer idjuez) {
        this.idjuez = idjuez;
    }

    public Integer getIdjuez() {
        return idjuez;
    }

    public void setIdjuez(Integer idjuez) {
        this.idjuez = idjuez;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjuez != null ? idjuez.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juez)) {
            return false;
        }
        Juez other = (Juez) object;
        if ((this.idjuez == null && other.idjuez != null) || (this.idjuez != null && !this.idjuez.equals(other.idjuez))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Juez[ idjuez=" + idjuez + " ]";
    }
    
}
