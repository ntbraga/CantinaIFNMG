package br.com.cantina.modeldb;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-11T15:11:43")
@StaticMetamodel(Transacao.class)
public class Transacao_ { 

    public static volatile SingularAttribute<Transacao, Integer> idtransacao;
    public static volatile SingularAttribute<Transacao, Integer> idcaixa;
    public static volatile SingularAttribute<Transacao, String> type;
    public static volatile SingularAttribute<Transacao, Integer> idcliente;
    public static volatile SingularAttribute<Transacao, Double> value;
    public static volatile SingularAttribute<Transacao, Date> timestamp;

}