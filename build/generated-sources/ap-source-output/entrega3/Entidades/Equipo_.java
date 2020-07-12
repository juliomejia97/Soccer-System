package entrega3.Entidades;

import entrega3.Entidades.DirectorTecnico;
import entrega3.Entidades.Jugador;
import entrega3.Entidades.Partidoxequipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Equipo.class)
public class Equipo_ { 

    public static volatile SingularAttribute<Equipo, Integer> posiciontabla;
    public static volatile SingularAttribute<Equipo, String> nombreequipo;
    public static volatile CollectionAttribute<Equipo, Partidoxequipo> partidoxequipoCollection;
    public static volatile CollectionAttribute<Equipo, DirectorTecnico> directorTecnicoCollection;
    public static volatile CollectionAttribute<Equipo, Jugador> jugadorCollection;
    public static volatile SingularAttribute<Equipo, Character> grupo;
    public static volatile SingularAttribute<Equipo, Integer> idequipo;

}