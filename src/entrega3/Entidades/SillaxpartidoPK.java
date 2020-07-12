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
public class SillaxpartidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private int idpartido;
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private int idestadio;
    @Basic(optional = false)
    @Column(name = "IDSILLA")
    private int idsilla;

    public SillaxpartidoPK() {
    }

    public SillaxpartidoPK(int idpartido, int idestadio, int idsilla) {
        this.idpartido = idpartido;
        this.idestadio = idestadio;
        this.idsilla = idsilla;
    }

    public int getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(int idpartido) {
        this.idpartido = idpartido;
    }

    public int getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(int idestadio) {
        this.idestadio = idestadio;
    }

    public int getIdsilla() {
        return idsilla;
    }

    public void setIdsilla(int idsilla) {
        this.idsilla = idsilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpartido;
        hash += (int) idestadio;
        hash += (int) idsilla;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SillaxpartidoPK)) {
            return false;
        }
        SillaxpartidoPK other = (SillaxpartidoPK) object;
        if (this.idpartido != other.idpartido) {
            return false;
        }
        if (this.idestadio != other.idestadio) {
            return false;
        }
        if (this.idsilla != other.idsilla) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.SillaxpartidoPK[ idpartido=" + idpartido + ", idestadio=" + idestadio + ", idsilla=" + idsilla + " ]";
    }
    
}
