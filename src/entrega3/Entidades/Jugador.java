/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "JUGADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j")
    , @NamedQuery(name = "Jugador.findByIdjugador", query = "SELECT j FROM Jugador j WHERE j.idjugador = :idjugador")
    , @NamedQuery(name = "Jugador.findByFechanacimiento", query = "SELECT j FROM Jugador j WHERE j.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "Jugador.findByNumerojugador", query = "SELECT j FROM Jugador j WHERE j.numerojugador = :numerojugador")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDJUGADOR")
    private Integer idjugador;
    @Basic(optional = false)
    @Column(name = "FECHANACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechanacimiento;
    @Lob
    @Column(name = "FOTO")
    private byte[] foto;
    @Column(name = "NUMEROJUGADOR")
    private Short numerojugador;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")
    @ManyToOne
    private Equipo idequipo;
    @JoinColumn(name = "IDJUGADOR", referencedColumnName = "IDPERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Jugador() {
    }

    public Jugador(Integer idjugador) {
        this.idjugador = idjugador;
    }

    public Jugador(Integer idjugador, Date fechanacimiento) {
        this.idjugador = idjugador;
        this.fechanacimiento = fechanacimiento;
    }

    public Integer getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(Integer idjugador) {
        this.idjugador = idjugador;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Short getNumerojugador() {
        return numerojugador;
    }

    public void setNumerojugador(Short numerojugador) {
        this.numerojugador = numerojugador;
    }

    public Equipo getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Equipo idequipo) {
        this.idequipo = idequipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjugador != null ? idjugador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.idjugador == null && other.idjugador != null) || (this.idjugador != null && !this.idjugador.equals(other.idjugador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Jugador[ idjugador=" + idjugador + " ]";
    }
    
}
