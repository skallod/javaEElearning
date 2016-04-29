package com.mycompany.mywebapp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gridnine.bof.gwt.common.vo.proposal.BookingProposalVo;
import com.gridnine.bof.gwt.common.vo.proposal.PostVo;

public class BookingProposalPanel {
	
	private Panel panel = new FlexTable();
	private List<VerticalPanel> items = new ArrayList<VerticalPanel>();
	
	
	
	public Panel getPanel() {
		return panel;
	}
	
	public void readData(BookingProposalVo bookingProposalVo){
		panel.clear();
		items.clear();
		for(PostVo postvo : bookingProposalVo.getPosts()){
			postvo;
		}
	}
	
	public void writeData(BookingProposalVo bookingProposalVo){
	}
	

//	public void refreshContent (){
//	}

//	public void setPanel(Panel panel) {
//		this.panel = panel;
//	}
	
	

}
