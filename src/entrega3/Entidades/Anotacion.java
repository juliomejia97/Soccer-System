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
@Table(name = "ANOTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anotacion.findAll", query = "SELECT a FROM Anotacion a")
    , @NamedQuery(name = "Anotacion.findByIdanotacion", query = "SELECT a FROM Anotacion a WHERE a.idanotacion = :idanotacion")
    , @NamedQuery(name = "Anotacion.findByMinuto", query = "SELECT a FROM Anotacion a WHERE a.minuto = :minuto")
    , @NamedQuery(name = "Anotacion.findByTiempo", query = "SELECT a FROM Anotacion a WHERE a.tiempo = :tiempo")
    , @NamedQuery(name = "Anotacion.findByTipogol", query = "SELECT a FROM Anotacion a WHERE a.tipogol = :tipogol")
    , @NamedQuery(name = "Anotacion.findByUsovar", query = "SELECT a FROM Anotacion a WHERE a.usovar = :usovar")})
public class Anotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDANOTACION")
    private Integer idanotacion;
    @Column(name = "MINUTO")
    private Short minuto;
    @Column(name = "TIEMPO")
    private Short tiempo;
    @Column(name = "TIPOGOL")
    private String tipogol;
    @Column(name = "USOVAR")
    private String usovar;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDJUGADOR")
    @ManyToOne
    private Jugador idjugador;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO")
    @ManyToOne
    private Partido idpartido;

    public Anotacion() {
    }

    public Anotacion(Integer idanotacion) {
        this.idanotacion = idanotacion;
    }

    public Integer getIdanotacion() {
        return idanotacion;
    }

    public void setIdanotacion(Integer idanotacion) {
        this.idanotacion = idanotacion;
    }

    public Short getMinuto() {
        return minuto;
    }

    public void setMinuto(Short minuto) {
        this.minuto = minuto;
    }

    public Short getTiempo() {
        return tiempo;
    }

    public void setTiempo(Short tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipogol() {
        return tipogol;
    }

    public void setTipogol(String tipogol) {
        this.tipogol = tipogol;
    }

    public String getUsovar() {
        return usovar;
    }

    public void setUsovar(String usovar) {
        this.usovar = usovar;
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
        hash += (idanotacion != null ? idanotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anotacion)) {
            return false;
        }
        Anotacion other = (Anotacion) object;
        if ((this.idanotacion == null && other.idanotacion != null) || (this.idanotacion != null && !this.idanotacion.equals(other.idanotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Anotacion[ idanotacion=" + idanotacion + " ]";
    }
    
}
