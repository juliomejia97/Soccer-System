package entrega3.Entidades;

import entrega3.Entidades.Entrada;
import entrega3.Entidades.Partido;
import entrega3.Entidades.Precio;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Fase.class)
public class Fase_ { 

    public static volatile CollectionAttribute<Fase, Partido> partidoCollection;
    public static volatile SingularAttribute<Fase, String> nombrefase;
    public static volatile CollectionAttribute<Fase, Precio> precioCollection;
    public static volatile SingularAttribute<Fase, Integer> idfase;
    public static volatile CollectionAttribute<Fase, Entrada> entradaCollection;

}