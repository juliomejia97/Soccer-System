package entrega3.Entidades;

import entrega3.Entidades.Banco;
import entrega3.Entidades.Cliente;
import entrega3.Entidades.Pago;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Tarjetacredito.class)
public class Tarjetacredito_ { 

    public static volatile SingularAttribute<Tarjetacredito, Integer> numerotarjeta;
    public static volatile SingularAttribute<Tarjetacredito, Banco> idbanco;
    public static volatile CollectionAttribute<Tarjetacredito, Pago> pagoCollection;
    public static volatile CollectionAttribute<Tarjetacredito, Cliente> clienteCollection;

}