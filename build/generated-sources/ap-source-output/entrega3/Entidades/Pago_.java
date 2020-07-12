package entrega3.Entidades;

import entrega3.Entidades.Tarjetacredito;
import entrega3.Entidades.Tiquete;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, Tarjetacredito> numerotarjeta;
    public static volatile SingularAttribute<Pago, Integer> idpago;
    public static volatile SingularAttribute<Pago, Tiquete> idtiquete;
    public static volatile SingularAttribute<Pago, BigDecimal> valor;

}