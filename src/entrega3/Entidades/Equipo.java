/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "EQUIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e")
    , @NamedQuery(name = "Equipo.findByIdequipo", query = "SELECT e FROM Equipo e WHERE e.idequipo = :idequipo")
    , @NamedQuery(name = "Equipo.findByNombreequipo", query = "SELECT e FROM Equipo e WHERE e.nombreequipo = :nombreequipo")
    , @NamedQuery(name = "Equipo.findByGrupo", query = "SELECT e FROM Equipo e WHERE e.grupo = :grupo")
    , @NamedQuery(name = "Equipo.findByPosiciontabla", query = "SELECT e FROM Equipo e WHERE e.posiciontabla = :posiciontabla")})
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDEQUIPO")
    private Integer idequipo;
    @Basic(optional = false)
    @Column(name = "NOMBREEQUIPO")
    private String nombreequipo;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private Character grupo;
    @Column(name = "POSICIONTABLA")
    private Integer posiciontabla;
    @OneToMany(mappedBy = "idequipo")
    private Collection<Jugador> jugadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipo")
    private Collection<Partidoxequipo> partidoxequipoCollection;
    @OneToMany(mappedBy = "idequipo")
    private Collection<DirectorTecnico> directorTecnicoCollection;

    public Equipo() {
    }

    public Equipo(Integer idequipo) {
        this.idequipo = idequipo;
    }

    public Equipo(Integer idequipo, String nombreequipo, Character grupo) {
        this.idequipo = idequipo;
        this.nombreequipo = nombreequipo;
        this.grupo = grupo;
    }

    public Integer getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Integer idequipo) {
        this.idequipo = idequipo;
    }

    public String getNombreequipo() {
        return nombreequipo;
    }

    public void setNombreequipo(String nombreequipo) {
        this.nombreequipo = nombreequipo;
    }

    public Character getGrupo() {
        return grupo;
    }

    public void setGrupo(Character grupo) {
        this.grupo = grupo;
    }

    public Integer getPosiciontabla() {
        return posiciontabla;
    }

    public void setPosiciontabla(Integer posiciontabla) {
        this.posiciontabla = posiciontabla;
    }

    @XmlTransient
    public Collection<Jugador> getJugadorCollection() {
        return jugadorCollection;
    }

    public void setJugadorCollection(Collection<Jugador> jugadorCollection) {
        this.jugadorCollection = jugadorCollection;
    }

    @XmlTransient
    public Collection<Partidoxequipo> getPartidoxequipoCollection() {
        return partidoxequipoCollection;
    }

    public void setPartidoxequipoCollection(Collection<Partidoxequipo> partidoxequipoCollection) {
        this.partidoxequipoCollection = partidoxequipoCollection;
    }

    @XmlTransient
    public Collection<DirectorTecnico> getDirectorTecnicoCollection() {
        return directorTecnicoCollection;
    }

    public void setDirectorTecnicoCollection(Collection<DirectorTecnico> directorTecnicoCollection) {
        this.directorTecnicoCollection = directorTecnicoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idequipo != null ? idequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.idequipo == null && other.idequipo != null) || (this.idequipo != null && !this.idequipo.equals(other.idequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Equipo[ idequipo=" + idequipo + " ]";
    }
    
}
