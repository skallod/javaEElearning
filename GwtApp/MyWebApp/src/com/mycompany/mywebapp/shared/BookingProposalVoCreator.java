package com.mycompany.mywebapp.shared;

import java.util.Date;
import java.util.List;

import com.mycompany.mywebapp.shared.vo.EntityReferenceVo;
import com.mycompany.mywebapp.shared.vo.proposal.BookingProposalVo;
import com.mycompany.mywebapp.shared.vo.proposal.PostVo;


public class BookingProposalVoCreator {
	public static BookingProposalVo create(){

		BookingProposalVo bookingProposalVo = new BookingProposalVo();
		bookingProposalVo.setCreateDate(new Date());
		PostVo postVo1;
		postVo1 = new PostVo();
		postVo1.setDate(new Date());
		postVo1.setMessage("message 1");
		EntityReferenceVo entityReferenceVo1 = new EntityReferenceVo();
		entityReferenceVo1.setCaption("user1");
		postVo1.setAuthor(entityReferenceVo1);
		bookingProposalVo.getPosts().add(postVo1);
		PostVo postVo2 = new PostVo();
		postVo2.setDate(new Date());
		postVo2.setMessage("message 2");
		EntityReferenceVo entityReferenceVo2 = new EntityReferenceVo();
		entityReferenceVo2.setCaption("user2");
		postVo1.setAuthor(entityReferenceVo2);
		bookingProposalVo.getPosts().add(postVo2);
		
		return bookingProposalVo;

//		private String fullNumber;
//
//	    private Date createDate;
//
//	    private EntityReferenceVo bookingFile;
//
//	    private List<PostVo> posts;
//		bookingProposalVo
	}

}
