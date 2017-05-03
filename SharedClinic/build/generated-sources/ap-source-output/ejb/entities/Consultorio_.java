package ejb.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consultorio.class)
public abstract class Consultorio_ {

	public static volatile SingularAttribute<Consultorio, String> endereco;
	public static volatile SingularAttribute<Consultorio, String> nome;
	public static volatile SingularAttribute<Consultorio, Long> id;
	public static volatile ListAttribute<Consultorio, Agenda> agenda;

}

