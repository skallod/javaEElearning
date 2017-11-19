package ru.galuzin.payment_system.common_types;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;

@Entity
public class EntityData {
    @Id
    private String uid;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Index(name = "EntityData_created")
    private final Date created = new Date();

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Index(name = "EntityData_modified")
    private Date modified;

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String modifiedBy;

    @Column(nullable = false)
    @org.hibernate.annotations.Index(name = "EntityData_entityType")
    private String entityType;

    private String lastVersionNotes;

    private String lastVersionDataSource;

    @Column(nullable = false)
    private int versionsCount;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVersionCreated;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVersionModified;

    @Column(nullable = false)
    private String lastVersionCreatedBy;

    @Column(nullable = false)
    private String lastVersionModifiedBy;

    @Column(nullable = false)
    private String lastVersionUid;

    @Lob
    @Column(length = 1024 * 1024 * 1024, nullable = false)
    private byte[] lastVersionData = new byte[0];

    @OneToMany(mappedBy = "entityData", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    private List<VersionData> versions;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setVersionsCount(int versionsCount) {
        this.versionsCount = versionsCount;
    }

    public void setLastVersionCreated(Date lastVersionCreated) {
        this.lastVersionCreated = lastVersionCreated;
    }

    public void setLastVersionModified(Date lastVersionModified) {
        this.lastVersionModified = lastVersionModified;
    }

    public void setLastVersionCreatedBy(String lastVersionCreatedBy) {
        this.lastVersionCreatedBy = lastVersionCreatedBy;
    }

    public void setLastVersionModifiedBy(String lastVersionModifiedBy) {
        this.lastVersionModifiedBy = lastVersionModifiedBy;
    }

    public void setLastVersionUid(String lastVersionUid) {
        this.lastVersionUid = lastVersionUid;
    }

    public void setLastVersionData(byte[] lastVersionData) {
        this.lastVersionData = lastVersionData;
    }

    public List<VersionData> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionData> versions) {
        this.versions = versions;
    }
}