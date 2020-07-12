package entrega3.Entidades;

import entrega3.Entidades.Equipo;
import entrega3.Entidades.Persona;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Jugador.class)
public class Jugador_ { 

    public static volatile SingularAttribute<Jugador, Short> numerojugador;
    public static volatile SingularAttribute<Jugador, Persona> persona;
    public static volatile SingularAttribute<Jugador, Date> fechanacimiento;
    public static volatile SingularAttribute<Jugador, byte[]> foto;
    public static volatile SingularAttribute<Jugador, Equipo> idequipo;
    public static volatile SingularAttribute<Jugador, Integer> idjugador;

}