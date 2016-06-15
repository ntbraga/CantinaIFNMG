package br.com.cantina.modeldb;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-15T14:25:31")
@StaticMetamodel(Caixa.class)
public class Caixa_ { 

    public static volatile SingularAttribute<Caixa, Boolean> aberto;
    public static volatile SingularAttribute<Caixa, Integer> idcaixa;
    public static volatile SingularAttribute<Caixa, Date> timeopen;
    public static volatile SingularAttribute<Caixa, Date> timeclose;

}