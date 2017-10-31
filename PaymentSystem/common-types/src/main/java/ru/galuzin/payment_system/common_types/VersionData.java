package ru.galuzin.payment_system.common_types;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class VersionData {
    @Id
    private String uid;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    private EntityData entityData;

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEntityData(EntityData entityData) {
        this.entityData = entityData;
    }
}
