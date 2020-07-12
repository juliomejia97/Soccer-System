/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByIdpago", query = "SELECT p FROM Pago p WHERE p.idpago = :idpago")
    , @NamedQuery(name = "Pago.findByValor", query = "SELECT p FROM Pago p WHERE p.valor = :valor")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPAGO")
    private Integer idpago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @JoinColumn(name = "NUMEROTARJETA", referencedColumnName = "NUMEROTARJETA")
    @ManyToOne
    private Tarjetacredito numerotarjeta;
    @JoinColumn(name = "IDTIQUETE", referencedColumnName = "IDTIQUETE")
    @ManyToOne
    private Tiquete idtiquete;

    public Pago() {
    }

    public Pago(Integer idpago) {
        this.idpago = idpago;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Tarjetacredito getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(Tarjetacredito numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
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
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Pago[ idpago=" + idpago + " ]";
    }
    
}
