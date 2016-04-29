package com.gridnine.bof.gwt.common.vo.proposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gridnine.bof.gwt.common.vo.BaseEntityVo;
import com.gridnine.bof.gwt.common.vo.EntityReferenceVo;

public class BaseProposalVo extends BaseEntityVo {

    private static final long serialVersionUID = 2357147637007558701L;

    private String fullNumber;

    private Date createDate;

    private EntityReferenceVo bookingFile;

    private List<PostVo> posts;

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(final String value) {
        fullNumber = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date value) {
        createDate = value;
    }

    private BaseProposalStatusVo status;

    public BaseProposalStatusVo getStatus() {
        return status;
    }

    public void setStatus(final BaseProposalStatusVo value) {
        this.status = value;
    }

    public void setBookingFile(final EntityReferenceVo value) {
        this.bookingFile = value;
    }

    public EntityReferenceVo getBookingFile() {
        return bookingFile;
    }

    public List<PostVo> getPosts() {
        if (posts == null) {
            posts = new ArrayList<PostVo>();
        }
        return posts;
    }

}
