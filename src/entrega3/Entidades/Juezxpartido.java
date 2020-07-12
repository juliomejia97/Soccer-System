/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "JUEZXPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juezxpartido.findAll", query = "SELECT j FROM Juezxpartido j")
    , @NamedQuery(name = "Juezxpartido.findByIdjuez", query = "SELECT j FROM Juezxpartido j WHERE j.juezxpartidoPK.idjuez = :idjuez")
    , @NamedQuery(name = "Juezxpartido.findByIdpartido", query = "SELECT j FROM Juezxpartido j WHERE j.juezxpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Juezxpartido.findByTipojuez", query = "SELECT j FROM Juezxpartido j WHERE j.tipojuez = :tipojuez")})
public class Juezxpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JuezxpartidoPK juezxpartidoPK;
    @Basic(optional = false)
    @Column(name = "TIPOJUEZ")
    private String tipojuez;

    public Juezxpartido() {
    }

    public Juezxpartido(JuezxpartidoPK juezxpartidoPK) {
        this.juezxpartidoPK = juezxpartidoPK;
    }

    public Juezxpartido(JuezxpartidoPK juezxpartidoPK, String tipojuez) {
        this.juezxpartidoPK = juezxpartidoPK;
        this.tipojuez = tipojuez;
    }

    public Juezxpartido(int idjuez, int idpartido) {
        this.juezxpartidoPK = new JuezxpartidoPK(idjuez, idpartido);
    }

    public JuezxpartidoPK getJuezxpartidoPK() {
        return juezxpartidoPK;
    }

    public void setJuezxpartidoPK(JuezxpartidoPK juezxpartidoPK) {
        this.juezxpartidoPK = juezxpartidoPK;
    }

    public String getTipojuez() {
        return tipojuez;
    }

    public void setTipojuez(String tipojuez) {
        this.tipojuez = tipojuez;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (juezxpartidoPK != null ? juezxpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juezxpartido)) {
            return false;
        }
        Juezxpartido other = (Juezxpartido) object;
        if ((this.juezxpartidoPK == null && other.juezxpartidoPK != null) || (this.juezxpartidoPK != null && !this.juezxpartidoPK.equals(other.juezxpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Juezxpartido[ juezxpartidoPK=" + juezxpartidoPK + " ]";
    }
    
}
