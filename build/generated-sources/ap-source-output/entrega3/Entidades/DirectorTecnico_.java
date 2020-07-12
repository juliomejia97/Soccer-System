package entrega3.Entidades;

import entrega3.Entidades.Equipo;
import entrega3.Entidades.Persona;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(DirectorTecnico.class)
public class DirectorTecnico_ { 

    public static volatile SingularAttribute<DirectorTecnico, String> tipo;
    public static volatile SingularAttribute<DirectorTecnico, Persona> persona;
    public static volatile SingularAttribute<DirectorTecnico, Integer> idDirector;
    public static volatile SingularAttribute<DirectorTecnico, Equipo> idequipo;

}