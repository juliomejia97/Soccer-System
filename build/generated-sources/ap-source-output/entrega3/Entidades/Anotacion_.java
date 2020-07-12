package entrega3.Entidades;

import entrega3.Entidades.Jugador;
import entrega3.Entidades.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Anotacion.class)
public class Anotacion_ { 

    public static volatile SingularAttribute<Anotacion, Integer> idanotacion;
    public static volatile SingularAttribute<Anotacion, Short> minuto;
    public static volatile SingularAttribute<Anotacion, Short> tiempo;
    public static volatile SingularAttribute<Anotacion, Partido> idpartido;
    public static volatile SingularAttribute<Anotacion, String> usovar;
    public static volatile SingularAttribute<Anotacion, Jugador> idjugador;
    public static volatile SingularAttribute<Anotacion, String> tipogol;

}