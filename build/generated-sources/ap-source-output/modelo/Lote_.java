package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Produto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-11T21:35:21")
@StaticMetamodel(Lote.class)
public class Lote_ { 

    public static volatile SingularAttribute<Lote, Date> dataModificacao;
    public static volatile SingularAttribute<Lote, String> numeroLote;
    public static volatile SingularAttribute<Lote, Integer> usrModificacao;
    public static volatile SingularAttribute<Lote, Produto> prod;
    public static volatile SingularAttribute<Lote, Date> dataValidade;
    public static volatile SingularAttribute<Lote, Date> dataEntrada;
    public static volatile SingularAttribute<Lote, String> justificativaEdit;
    public static volatile SingularAttribute<Lote, Integer> idLote;
    public static volatile SingularAttribute<Lote, Integer> quantidade;

}