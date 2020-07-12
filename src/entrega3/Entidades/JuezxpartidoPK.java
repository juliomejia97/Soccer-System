/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author DELL
 */
@Embeddable
public class JuezxpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDJUEZ")
    private int idjuez;
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private int idpartido;

    public JuezxpartidoPK() {
    }

    public JuezxpartidoPK(int idjuez, int idpartido) {
        this.idjuez = idjuez;
        this.idpartido = idpartido;
    }

    public int getIdjuez() {
        return idjuez;
    }

    public void setIdjuez(int idjuez) {
        this.idjuez = idjuez;
    }

    public int getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(int idpartido) {
        this.idpartido = idpartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idjuez;
        hash += (int) idpartido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JuezxpartidoPK)) {
            return false;
        }
        JuezxpartidoPK other = (JuezxpartidoPK) object;
        if (this.idjuez != other.idjuez) {
            return false;
        }
        if (this.idpartido != other.idpartido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.JuezxpartidoPK[ idjuez=" + idjuez + ", idpartido=" + idpartido + " ]";
    }
    
}
