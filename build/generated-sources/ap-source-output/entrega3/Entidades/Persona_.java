package entrega3.Entidades;

import entrega3.Entidades.Cliente;
import entrega3.Entidades.DirectorTecnico;
import entrega3.Entidades.Juez;
import entrega3.Entidades.Jugador;
import entrega3.Entidades.Pais;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, String> apellidos;
    public static volatile SingularAttribute<Persona, Cliente> cliente;
    public static volatile SingularAttribute<Persona, Juez> juez;
    public static volatile SingularAttribute<Persona, Pais> idpais;
    public static volatile SingularAttribute<Persona, Jugador> jugador;
    public static volatile SingularAttribute<Persona, Integer> idpersona;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, DirectorTecnico> directorTecnico;

}