package entrega3.Entidades;

import entrega3.Entidades.Cliente;
import entrega3.Entidades.Entrada;
import entrega3.Entidades.Pago;
import entrega3.Entidades.Partido;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Tiquete.class)
public class Tiquete_ { 

    public static volatile SingularAttribute<Tiquete, Date> fechahora;
    public static volatile SingularAttribute<Tiquete, Integer> idvisitante;
    public static volatile SingularAttribute<Tiquete, Partido> idpartido;
    public static volatile SingularAttribute<Tiquete, Integer> idtiquete;
    public static volatile SingularAttribute<Tiquete, Integer> idlocal;
    public static volatile SingularAttribute<Tiquete, Cliente> idcliente;
    public static volatile CollectionAttribute<Tiquete, Entrada> entradaCollection;
    public static volatile CollectionAttribute<Tiquete, Pago> pagoCollection;

}