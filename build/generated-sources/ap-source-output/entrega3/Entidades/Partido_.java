package entrega3.Entidades;

import entrega3.Entidades.Alineacion;
import entrega3.Entidades.Anotacion;
import entrega3.Entidades.Estadio;
import entrega3.Entidades.Fase;
import entrega3.Entidades.Partidoxequipo;
import entrega3.Entidades.Tarjeta;
import entrega3.Entidades.Tiquete;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Partido.class)
public class Partido_ { 

    public static volatile SingularAttribute<Partido, Date> fecha;
    public static volatile SingularAttribute<Partido, Integer> idvisitante;
    public static volatile CollectionAttribute<Partido, Tarjeta> tarjetaCollection;
    public static volatile CollectionAttribute<Partido, Alineacion> alineacionCollection;
    public static volatile CollectionAttribute<Partido, Partidoxequipo> partidoxequipoCollection;
    public static volatile CollectionAttribute<Partido, Anotacion> anotacionCollection;
    public static volatile SingularAttribute<Partido, Integer> idpartido;
    public static volatile SingularAttribute<Partido, Fase> idfase;
    public static volatile SingularAttribute<Partido, Integer> idlocal;
    public static volatile CollectionAttribute<Partido, Tiquete> tiqueteCollection;
    public static volatile SingularAttribute<Partido, Estadio> idestadio;

}