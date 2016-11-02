package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Livro;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T18:59:39")
@StaticMetamodel(Retiradas.class)
public class Retiradas_ { 

    public static volatile SingularAttribute<Retiradas, Cliente> cliente;
    public static volatile SingularAttribute<Retiradas, Livro> livro;
    public static volatile SingularAttribute<Retiradas, Date> dataRetirada;
    public static volatile SingularAttribute<Retiradas, Long> id;
    public static volatile SingularAttribute<Retiradas, Date> dataDevolucao;

}