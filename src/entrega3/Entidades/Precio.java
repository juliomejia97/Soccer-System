/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "PRECIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Precio.findAll", query = "SELECT p FROM Precio p")
    , @NamedQuery(name = "Precio.findByIdcategoria", query = "SELECT p FROM Precio p WHERE p.precioPK.idcategoria = :idcategoria")
    , @NamedQuery(name = "Precio.findByIdfase", query = "SELECT p FROM Precio p WHERE p.precioPK.idfase = :idfase")
    , @NamedQuery(name = "Precio.findByPrecio", query = "SELECT p FROM Precio p WHERE p.precio = :precio")})
public class Precio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrecioPK precioPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "IDFASE", referencedColumnName = "IDFASE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Fase fase;

    public Precio() {
    }

    public Precio(PrecioPK precioPK) {
        this.precioPK = precioPK;
    }

    public Precio(int idcategoria, int idfase) {
        this.precioPK = new PrecioPK(idcategoria, idfase);
    }

    public PrecioPK getPrecioPK() {
        return precioPK;
    }

    public void setPrecioPK(PrecioPK precioPK) {
        this.precioPK = precioPK;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (precioPK != null ? precioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precio)) {
            return false;
        }
        Precio other = (Precio) object;
        if ((this.precioPK == null && other.precioPK != null) || (this.precioPK != null && !this.precioPK.equals(other.precioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Precio[ precioPK=" + precioPK + " ]";
    }
    
}
