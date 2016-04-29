package com.gridnine.bof.gwt.common.vo.proposal;

import java.util.Date;

import com.gridnine.bof.gwt.common.vo.BaseEntityVo;
import com.gridnine.bof.gwt.common.vo.EntityReferenceVo;

public class PostVo extends BaseEntityVo {
    private static final long serialVersionUID = -8530294134395644845L;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(final Date value) {
        date = value;
    }

    private EntityReferenceVo author;

    public EntityReferenceVo getAuthor() {
        return author;
    }

    public void setAuthor(final EntityReferenceVo value) {
        this.author = value;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String value) {
        message = value;
    }

    private boolean isSystem;

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(final boolean value) {
        isSystem = value;
    }

}
