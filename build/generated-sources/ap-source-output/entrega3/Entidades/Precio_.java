package entrega3.Entidades;

import entrega3.Entidades.Categoria;
import entrega3.Entidades.Fase;
import entrega3.Entidades.PrecioPK;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Precio.class)
public class Precio_ { 

    public static volatile SingularAttribute<Precio, BigDecimal> precio;
    public static volatile SingularAttribute<Precio, Fase> fase;
    public static volatile SingularAttribute<Precio, PrecioPK> precioPK;
    public static volatile SingularAttribute<Precio, Categoria> categoria;

}