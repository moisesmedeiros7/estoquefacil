package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Produto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-11T21:35:21")
@StaticMetamodel(Estoque.class)
public class Estoque_ { 

    public static volatile SingularAttribute<Estoque, String> loc;
    public static volatile SingularAttribute<Estoque, String> telefone;
    public static volatile SingularAttribute<Estoque, Integer> idEstoque;
    public static volatile SingularAttribute<Estoque, String> endereco;
    public static volatile SingularAttribute<Estoque, String> nome;
    public static volatile CollectionAttribute<Estoque, Produto> produtoCollection;
    public static volatile SingularAttribute<Estoque, String> descricao;

}