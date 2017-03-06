package ru.galuzin.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.galuzin.entity.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-19T16:18:23")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> lastName;
    public static volatile CollectionAttribute<Person, Account> accountCollection;
    public static volatile SingularAttribute<Person, String> address;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, Long> personId;

}