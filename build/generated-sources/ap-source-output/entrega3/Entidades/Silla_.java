package entrega3.Entidades;

import entrega3.Entidades.Categoria;
import entrega3.Entidades.Entrada;
import entrega3.Entidades.Estadio;
import entrega3.Entidades.SillaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Silla.class)
public class Silla_ { 

    public static volatile SingularAttribute<Silla, Short> ocupado;
    public static volatile SingularAttribute<Silla, Categoria> idcategoria;
    public static volatile SingularAttribute<Silla, SillaPK> sillaPK;
    public static volatile SingularAttribute<Silla, Estadio> estadio;
    public static volatile CollectionAttribute<Silla, Entrada> entradaCollection;

}