/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "EFECTIVO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Efectivo.findAll", query = "SELECT e FROM Efectivo e")
    , @NamedQuery(name = "Efectivo.findByIdpago", query = "SELECT e FROM Efectivo e WHERE e.idpago = :idpago")})
public class Efectivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPAGO")
    private Integer idpago;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "efectivo1")
    private Efectivo efectivo;
    @JoinColumn(name = "IDPAGO", referencedColumnName = "IDPAGO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Efectivo efectivo1;

    public Efectivo() {
    }

    public Efectivo(Integer idpago) {
        this.idpago = idpago;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public Efectivo getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Efectivo efectivo) {
        this.efectivo = efectivo;
    }

    public Efectivo getEfectivo1() {
        return efectivo1;
    }

    public void setEfectivo1(Efectivo efectivo1) {
        this.efectivo1 = efectivo1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Efectivo)) {
            return false;
        }
        Efectivo other = (Efectivo) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Efectivo[ idpago=" + idpago + " ]";
    }
    
}
