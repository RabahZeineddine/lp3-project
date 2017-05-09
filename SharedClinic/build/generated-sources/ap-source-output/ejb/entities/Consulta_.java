package ejb.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consulta.class)
public abstract class Consulta_ {

	public static volatile SingularAttribute<Consulta, Cliente> cliente;
	public static volatile SingularAttribute<Consulta, Long> id;
	public static volatile SingularAttribute<Consulta, Date> dataConsulta;
	public static volatile SingularAttribute<Consulta, Boolean> marcada;
	public static volatile SingularAttribute<Consulta, Agenda> agenda;

}

