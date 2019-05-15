package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Estoque;
import modelo.Lote;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-11T21:35:21")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, Integer> idProduto;
    public static volatile CollectionAttribute<Produto, Lote> loteCollection;
    public static volatile SingularAttribute<Produto, Estoque> est;
    public static volatile SingularAttribute<Produto, String> nome;
    public static volatile SingularAttribute<Produto, String> unidade;
    public static volatile SingularAttribute<Produto, Date> datavencLic;
    public static volatile SingularAttribute<Produto, String> licitavel;
    public static volatile SingularAttribute<Produto, String> descricao;
    public static volatile SingularAttribute<Produto, String> status;

}