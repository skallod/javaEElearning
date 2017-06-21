package ru.galuzin.payment_system.common_types;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "server",
        "category" }))
public class MetricsData extends BaseAssetData {
    @Column(nullable = false)
    @org.hibernate.annotations.Index(name = "MetricsData_server")
    private String server;

    @Column(nullable = false)
    @org.hibernate.annotations.Index(name = "MetricsData_category")
    private String category;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Index(name = "MetricsData_modified")
    private Date modified;

    @Column(length = 10485760)
    private String countings;

    private int valuesCount;


}
