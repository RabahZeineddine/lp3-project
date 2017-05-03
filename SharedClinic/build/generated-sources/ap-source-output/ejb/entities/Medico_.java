package ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Medico.class)
public abstract class Medico_ extends ejb.entities.Usuario_ {

	public static volatile SingularAttribute<Medico, Agenda> agenda;
	public static volatile SingularAttribute<Medico, String> crm;

}

