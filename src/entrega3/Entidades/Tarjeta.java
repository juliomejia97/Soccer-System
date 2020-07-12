/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
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
@Table(name = "TARJETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")
    , @NamedQuery(name = "Tarjeta.findByIdtarjeta", query = "SELECT t FROM Tarjeta t WHERE t.idtarjeta = :idtarjeta")
    , @NamedQuery(name = "Tarjeta.findByTipotarjeta", query = "SELECT t FROM Tarjeta t WHERE t.tipotarjeta = :tipotarjeta")
    , @NamedQuery(name = "Tarjeta.findByMinuto", query = "SELECT t FROM Tarjeta t WHERE t.minuto = :minuto")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTARJETA")
    private Integer idtarjeta;
    @Column(name = "TIPOTARJETA")
    private String tipotarjeta;
    @Column(name = "MINUTO")
    private Short minuto;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR")
    @ManyToOne
    private Jugador idjugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne
    private Partido idpartido;

    public Tarjeta() {
    }

    public Tarjeta(Integer idtarjeta) {
        this.idtarjeta = idtarjeta;
    }

    public Integer getIdtarjeta() {
        return idtarjeta;
    }

    public void setIdtarjeta(Integer idtarjeta) {
        this.idtarjeta = idtarjeta;
    }

    public String getTipotarjeta() {
        return tipotarjeta;
    }

    public void setTipotarjeta(String tipotarjeta) {
        this.tipotarjeta = tipotarjeta;
    }

    public Short getMinuto() {
        return minuto;
    }

    public void setMinuto(Short minuto) {
        this.minuto = minuto;
    }

    public Jugador getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(Jugador idjugador) {
        this.idjugador = idjugador;
    }

    public Partido getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Partido idpartido) {
        this.idpartido = idpartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtarjeta != null ? idtarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.idtarjeta == null && other.idtarjeta != null) || (this.idtarjeta != null && !this.idtarjeta.equals(other.idtarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Tarjeta[ idtarjeta=" + idtarjeta + " ]";
    }
    
}
