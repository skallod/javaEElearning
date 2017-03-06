package ru.galuzin.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.galuzin.entity.Operation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-19T16:18:23")
@StaticMetamodel(Terminal.class)
public class Terminal_ { 

    public static volatile SingularAttribute<Terminal, String> address;
    public static volatile CollectionAttribute<Terminal, Operation> operationCollection;
    public static volatile SingularAttribute<Terminal, Long> teminalId;

}