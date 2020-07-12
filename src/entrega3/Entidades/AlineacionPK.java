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
public class AlineacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDJUGADOR")
    private int idjugador;
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private int idpartido;

    public AlineacionPK() {
    }

    public AlineacionPK(int idjugador, int idpartido) {
        this.idjugador = idjugador;
        this.idpartido = idpartido;
    }

    public int getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(int idjugador) {
        this.idjugador = idjugador;
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
        hash += (int) idjugador;
        hash += (int) idpartido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlineacionPK)) {
            return false;
        }
        AlineacionPK other = (AlineacionPK) object;
        if (this.idjugador != other.idjugador) {
            return false;
        }
        if (this.idpartido != other.idpartido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.AlineacionPK[ idjugador=" + idjugador + ", idpartido=" + idpartido + " ]";
    }
    
}
