package ru.galuzin.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.galuzin.entity.Operation;
import ru.galuzin.entity.Person;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-19T16:18:23")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Long> accountId;
    public static volatile SingularAttribute<Account, Double> money;
    public static volatile CollectionAttribute<Account, Operation> operationCollection;
    public static volatile SingularAttribute<Account, Person> person;
    public static volatile CollectionAttribute<Account, Operation> operationCollection1;

}