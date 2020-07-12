package entrega3.Entidades;

import entrega3.Entidades.Equipo;
import entrega3.Entidades.Partido;
import entrega3.Entidades.PartidoxequipoPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Partidoxequipo.class)
public class Partidoxequipo_ { 

    public static volatile SingularAttribute<Partidoxequipo, Equipo> equipo;
    public static volatile SingularAttribute<Partidoxequipo, Short> visitante;
    public static volatile SingularAttribute<Partidoxequipo, Integer> goles;
    public static volatile SingularAttribute<Partidoxequipo, Partido> partido;
    public static volatile SingularAttribute<Partidoxequipo, PartidoxequipoPK> partidoxequipoPK;

}