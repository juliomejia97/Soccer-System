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
public class PartidoxequipoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private int idpartido;
    @Basic(optional = false)
    @Column(name = "IDEQUIPO")
    private int idequipo;

    public PartidoxequipoPK() {
    }

    public PartidoxequipoPK(int idpartido, int idequipo) {
        this.idpartido = idpartido;
        this.idequipo = idequipo;
    }

    public int getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(int idpartido) {
        this.idpartido = idpartido;
    }

    public int getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(int idequipo) {
        this.idequipo = idequipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpartido;
        hash += (int) idequipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartidoxequipoPK)) {
            return false;
        }
        PartidoxequipoPK other = (PartidoxequipoPK) object;
        if (this.idpartido != other.idpartido) {
            return false;
        }
        if (this.idequipo != other.idequipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.PartidoxequipoPK[ idpartido=" + idpartido + ", idequipo=" + idequipo + " ]";
    }
    
}
