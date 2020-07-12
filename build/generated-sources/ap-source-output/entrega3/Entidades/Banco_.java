package entrega3.Entidades;

import entrega3.Entidades.Tarjetacredito;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-25T01:18:46")
@StaticMetamodel(Banco.class)
public class Banco_ { 

    public static volatile SingularAttribute<Banco, Integer> idbanco;
    public static volatile SingularAttribute<Banco, String> nombrebanco;
    public static volatile CollectionAttribute<Banco, Tarjetacredito> tarjetacreditoCollection;

}