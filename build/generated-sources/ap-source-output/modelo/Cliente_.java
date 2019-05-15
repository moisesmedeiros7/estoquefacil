package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-11T21:35:21")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> senha;
    public static volatile SingularAttribute<Cliente, String> cidade;
    public static volatile SingularAttribute<Cliente, Integer> idCliente;
    public static volatile SingularAttribute<Cliente, String> endereco;
    public static volatile SingularAttribute<Cliente, String> nome;
    public static volatile SingularAttribute<Cliente, String> cpfCnpj;
    public static volatile CollectionAttribute<Cliente, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Cliente, String> email;

}