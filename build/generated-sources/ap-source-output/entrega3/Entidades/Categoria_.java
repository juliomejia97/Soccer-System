package entrega3.Entidades;

import entrega3.Entidades.Precio;
import entrega3.Entidades.Silla;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile CollectionAttribute<Categoria, Precio> precioCollection;
    public static volatile SingularAttribute<Categoria, String> nombrecategoria;
    public static volatile CollectionAttribute<Categoria, Silla> sillaCollection;
    public static volatile SingularAttribute<Categoria, Integer> idcategoria;

}