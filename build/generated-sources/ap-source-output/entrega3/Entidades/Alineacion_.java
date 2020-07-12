package entrega3.Entidades;

import entrega3.Entidades.AlineacionPK;
import entrega3.Entidades.Jugador;
import entrega3.Entidades.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Alineacion.class)
public class Alineacion_ { 

    public static volatile SingularAttribute<Alineacion, String> posicion;
    public static volatile SingularAttribute<Alineacion, Jugador> jugador;
    public static volatile SingularAttribute<Alineacion, AlineacionPK> alineacionPK;
    public static volatile SingularAttribute<Alineacion, Partido> partido;
    public static volatile SingularAttribute<Alineacion, Short> titular;

}