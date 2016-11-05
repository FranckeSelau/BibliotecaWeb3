package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Livro;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-05T17:11:54")
@StaticMetamodel(Devolucao.class)
public class Devolucao_ { 

    public static volatile SingularAttribute<Devolucao, Cliente> cliente;
    public static volatile SingularAttribute<Devolucao, Livro> livro;
    public static volatile SingularAttribute<Devolucao, Date> dataRetirada;
    public static volatile SingularAttribute<Devolucao, Long> id;
    public static volatile SingularAttribute<Devolucao, Date> dataDevolucao;
    public static volatile SingularAttribute<Devolucao, Date> dataDevolvido;

}