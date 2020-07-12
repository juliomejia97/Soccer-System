package entrega3.Entidades;

import entrega3.Entidades.Jugador;
import entrega3.Entidades.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Tarjeta.class)
public class Tarjeta_ { 

    public static volatile SingularAttribute<Tarjeta, Short> minuto;
    public static volatile SingularAttribute<Tarjeta, Partido> idpartido;
    public static volatile SingularAttribute<Tarjeta, Integer> idtarjeta;
    public static volatile SingularAttribute<Tarjeta, Jugador> idjugador;
    public static volatile SingularAttribute<Tarjeta, String> tipotarjeta;

}