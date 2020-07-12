package entrega3.Entidades;

import entrega3.Entidades.Persona;
import entrega3.Entidades.Tarjetacredito;
import entrega3.Entidades.Tiquete;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Persona> persona;
    public static volatile SingularAttribute<Cliente, Tarjetacredito> numerotarjeta;
    public static volatile SingularAttribute<Cliente, String> pasaporte;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, Integer> idcliente;
    public static volatile CollectionAttribute<Cliente, Tiquete> tiqueteCollection;
    public static volatile SingularAttribute<Cliente, String> email;

}