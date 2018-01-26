/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id$
 *****************************************************************/
package ru.galuzin.payment_system.common_types;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.util.Date;

import org.hibernate.annotations.Index;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "dictId", "code" }))
public class DictionaryData {
    @Id
    private String uid;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private final Date created = new Date();

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "DictionaryData_modified")
    private Date modified;

    @Column(nullable = false)
    @Index(name = "DictionaryData_dictId")
    private String dictId;

    @Column(nullable = false)
    @Index(name = "DictionaryData_code")
    private String code;

    @Column(nullable = false)
    @Index(name = "DictionaryData_deleted")
    private boolean deleted = false;

    @Lob
    @Column(length = 1024 * 1024 * 1024, nullable = false)
    private byte[] data = new byte[0];

    public DictionaryData() {
        // no-op
    }

    public String getUid() {
        return uid;
    }

    String getDictionaryId() {
        return dictId;
    }

    String getCode() {
        return code;
    }

    void setDeleted(final boolean value) {
        deleted = value;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
