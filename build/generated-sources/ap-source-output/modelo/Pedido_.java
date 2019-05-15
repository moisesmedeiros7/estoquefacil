package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Lote;
import modelo.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-11T21:35:21")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Lote> lot;
    public static volatile SingularAttribute<Pedido, Integer> qtde;
    public static volatile SingularAttribute<Pedido, Cliente> cli;
    public static volatile SingularAttribute<Pedido, Integer> numero;
    public static volatile SingularAttribute<Pedido, Date> dataPedido;
    public static volatile SingularAttribute<Pedido, Date> horaPedido;
    public static volatile SingularAttribute<Pedido, Usuario> us;

}