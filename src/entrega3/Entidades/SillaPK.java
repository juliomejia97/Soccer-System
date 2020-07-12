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
public class SillaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSILLA")
    private int idsilla;
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private int idestadio;

    public SillaPK() {
    }

    public SillaPK(int idsilla, int idestadio) {
        this.idsilla = idsilla;
        this.idestadio = idestadio;
    }

    public int getIdsilla() {
        return idsilla;
    }

    public void setIdsilla(int idsilla) {
        this.idsilla = idsilla;
    }

    public int getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(int idestadio) {
        this.idestadio = idestadio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idsilla;
        hash += (int) idestadio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SillaPK)) {
            return false;
        }
        SillaPK other = (SillaPK) object;
        if (this.idsilla != other.idsilla) {
            return false;
        }
        if (this.idestadio != other.idestadio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.SillaPK[ idsilla=" + idsilla + ", idestadio=" + idestadio + " ]";
    }
    
}
