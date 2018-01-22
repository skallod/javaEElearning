/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: bof-3
 *
 * $Id$
 *****************************************************************/
package com.gridnine.bof.server.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;

import com.gridnine.bof.common.model.dict.BaseDictionary;
import com.gridnine.bof.common.util.MiscUtil;
import com.gridnine.bof.common.xml.XUtil;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "dictId", "code" }))
class DictionaryData {
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

    DictionaryData() {
        // no-op
    }

    String getUid() {
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

    void toDictionary(final BaseDictionary dict) throws Exception {
        if (deleted) {
            throw new IllegalStateException("dictionary item marked as deleted"); //$NON-NLS-1$
        }
        dict.setUid(uid);
        if (data.length == 0) {
            dict.fromXML(XUtil.getDocumentBuilder().newDocument()
                .createElement("object")); //$NON-NLS-1$
        } else {
            dict.fromXML(XUtil.getDocumentBuilder()
                .parse(XUtil.createSource(new ByteArrayInputStream(data)))
                .getDocumentElement());
        }
        dict.setCreated(MiscUtil.cloneDate(created));
        dict.setModified(MiscUtil.cloneDate(modified));
    }

    void fromDictionary(final BaseDictionary dict) throws Exception {
        uid = dict.getUid();
        dictId = dict.getClass().getName();
        code = dict.getCode();
        {
            ByteArrayOutputStream strm = new ByteArrayOutputStream();
            XUtil.serialize(dict, strm);
            data = strm.toByteArray();
        }
    }

    void postSave(final BaseDictionary dict) {
        dict.setCreated(MiscUtil.cloneDate(created));
        dict.setModified(MiscUtil.cloneDate(modified));
    }
}
