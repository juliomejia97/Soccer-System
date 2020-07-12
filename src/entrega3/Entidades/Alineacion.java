/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
@Table(name = "ALINEACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alineacion.findAll", query = "SELECT a FROM Alineacion a")
    , @NamedQuery(name = "Alineacion.findByPosicion", query = "SELECT a FROM Alineacion a WHERE a.posicion = :posicion")
    , @NamedQuery(name = "Alineacion.findByTitular", query = "SELECT a FROM Alineacion a WHERE a.titular = :titular")
    , @NamedQuery(name = "Alineacion.findByIdjugador", query = "SELECT a FROM Alineacion a WHERE a.alineacionPK.idjugador = :idjugador")
    , @NamedQuery(name = "Alineacion.findByIdpartido", query = "SELECT a FROM Alineacion a WHERE a.alineacionPK.idpartido = :idpartido")})
public class Alineacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlineacionPK alineacionPK;
    @Column(name = "POSICION")
    private String posicion;
    @Column(name = "TITULAR")
    private Short titular;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jugador jugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Alineacion() {
    }

    public Alineacion(AlineacionPK alineacionPK) {
        this.alineacionPK = alineacionPK;
    }

    public Alineacion(int idjugador, int idpartido) {
        this.alineacionPK = new AlineacionPK(idjugador, idpartido);
    }

    public AlineacionPK getAlineacionPK() {
        return alineacionPK;
    }

    public void setAlineacionPK(AlineacionPK alineacionPK) {
        this.alineacionPK = alineacionPK;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Short getTitular() {
        return titular;
    }

    public void setTitular(Short titular) {
        this.titular = titular;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alineacionPK != null ? alineacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alineacion)) {
            return false;
        }
        Alineacion other = (Alineacion) object;
        if ((this.alineacionPK == null && other.alineacionPK != null) || (this.alineacionPK != null && !this.alineacionPK.equals(other.alineacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Alineacion[ alineacionPK=" + alineacionPK + " ]";
    }
    
}
