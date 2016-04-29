/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF Unifest
 *
 * $Id: $
 *****************************************************************/
package com.gridnine.bof.gwt.common.vo;

public class EntityReferenceVo extends BaseIdentityVo {

    private static final long serialVersionUID = 2269865995564503029L;

    private String caption;

    private String type;

    public String getCaption() {
        return caption;
    }

    public void setCaption(final String value) {
        caption = value;
    }

    public String getType() {
        return type;
    }

    public void setType(final String value) {
        type = value;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntityReferenceVo)) {
            return false;
        }
        EntityReferenceVo other = (EntityReferenceVo) obj;
        return getUid().equals(other.getUid())
            && getType().equals(other.getType());
    }

    @Override
    public int hashCode() {
        return getUid().hashCode();
    }

    @Override
    public String toString() {
        return getCaption();
    }
}
