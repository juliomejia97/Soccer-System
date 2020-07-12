package entrega3.Entidades;

import entrega3.Entidades.Ciudad;
import entrega3.Entidades.Partido;
import entrega3.Entidades.Silla;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Estadio.class)
public class Estadio_ { 

    public static volatile CollectionAttribute<Estadio, Partido> partidoCollection;
    public static volatile SingularAttribute<Estadio, String> nombreestadio;
    public static volatile CollectionAttribute<Estadio, Silla> sillaCollection;
    public static volatile SingularAttribute<Estadio, Ciudad> idciudad;
    public static volatile SingularAttribute<Estadio, Integer> idestadio;

}