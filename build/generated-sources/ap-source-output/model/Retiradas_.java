package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Livro;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-27T21:30:07")
@StaticMetamodel(Retiradas.class)
public class Retiradas_ { 

    public static volatile SingularAttribute<Retiradas, Cliente> cliente;
    public static volatile SingularAttribute<Retiradas, Livro> livro;
    public static volatile SingularAttribute<Retiradas, Long> id;

}