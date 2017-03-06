package ru.galuzin.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.galuzin.entity.Account;
import ru.galuzin.entity.Terminal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-19T16:18:23")
@StaticMetamodel(Operation.class)
public class Operation_ { 

    public static volatile SingularAttribute<Operation, Date> date;
    public static volatile SingularAttribute<Operation, Double> money;
    public static volatile SingularAttribute<Operation, Long> operationId;
    public static volatile SingularAttribute<Operation, Account> account1;
    public static volatile SingularAttribute<Operation, String> operationType;
    public static volatile SingularAttribute<Operation, Terminal> terminal;
    public static volatile SingularAttribute<Operation, Account> account;

}