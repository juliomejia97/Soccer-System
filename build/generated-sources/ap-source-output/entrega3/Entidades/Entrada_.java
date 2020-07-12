package entrega3.Entidades;

import entrega3.Entidades.Fase;
import entrega3.Entidades.Silla;
import entrega3.Entidades.Tiquete;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Entrada.class)
public class Entrada_ { 

    public static volatile SingularAttribute<Entrada, Integer> identrada;
    public static volatile SingularAttribute<Entrada, Fase> idfase;
    public static volatile SingularAttribute<Entrada, Tiquete> idtiquete;
    public static volatile SingularAttribute<Entrada, Silla> silla;

}