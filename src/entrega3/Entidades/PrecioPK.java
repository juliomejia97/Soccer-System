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
public class PrecioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDCATEGORIA")
    private int idcategoria;
    @Basic(optional = false)
    @Column(name = "IDFASE")
    private int idfase;

    public PrecioPK() {
    }

    public PrecioPK(int idcategoria, int idfase) {
        this.idcategoria = idcategoria;
        this.idfase = idfase;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public int getIdfase() {
        return idfase;
    }

    public void setIdfase(int idfase) {
        this.idfase = idfase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcategoria;
        hash += (int) idfase;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrecioPK)) {
            return false;
        }
        PrecioPK other = (PrecioPK) object;
        if (this.idcategoria != other.idcategoria) {
            return false;
        }
        if (this.idfase != other.idfase) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.PrecioPK[ idcategoria=" + idcategoria + ", idfase=" + idfase + " ]";
    }
    
}
