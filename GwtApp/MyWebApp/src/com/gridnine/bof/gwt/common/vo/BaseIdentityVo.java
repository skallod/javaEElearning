/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF Unifest
 *
 * $Id: $
 *****************************************************************/
package com.gridnine.bof.gwt.common.vo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class BaseIdentityVo implements Serializable, IsSerializable {
    private static final long serialVersionUID = -8376884853976144333L;

    public static enum Property {
        uid
    }

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(final String value) {
        uid = value;
    }

    protected String sortBy;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseIdentityVo)) {
            return false;
        }
        return uid.equals(((BaseIdentityVo) obj).uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public String toString() {
        return "{" + getClass().getName() + ": UID=" + getUid() + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void setSortBy(final String value) {
        sortBy = value;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Object getValue() {
        if (Property.uid.name().equalsIgnoreCase(sortBy)) {
            return getUid();
        }
        throw new IllegalArgumentException("Field " + sortBy + " not found"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
