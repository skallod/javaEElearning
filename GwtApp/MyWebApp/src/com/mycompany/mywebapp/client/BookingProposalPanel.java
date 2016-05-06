package com.mycompany.mywebapp.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mycompany.mywebapp.shared.vo.proposal.BookingProposalVo;
import com.mycompany.mywebapp.shared.vo.proposal.PostVo;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;


public class BookingProposalPanel {
//	private static final Logger logger = Logger.getLogger("");
//    logger.log(Level.SEVERE, "this message should get logged");
	
	final private Panel panel;
//	final private List<VerticalPanel> items = new ArrayList<VerticalPanel>();
	
	public BookingProposalPanel(){
		panel = new FlexTable();
	    FlexCellFormatter cellFormatter = ((FlexTable)panel).getFlexCellFormatter();
//	    panel.addStyleName("flexTable");
//	    panel.setWidth("32em");
//	    ((FlexTable)panel).setCellSpacing(5);
//	    ((FlexTable)panel).setCellPadding(3);

	    // Add some text
//	    cellFormatter.setHorizontalAlignment(
//	    0, 1, HasHorizontalAlignment.ALIGN_LEFT);
//	    ((FlexTable)panel).setHTML(0, 0, "Posts table"
//	    );
//	    cellFormatter.setColSpan(0, 0, 2);
	    
	    final TextBox testField = new TextBox();
	    testField.setText("TEST TEST");
	    RootPanel.get().add(testField);
	    ((FlexTable)panel).setWidget(1, 0, testField);
	    
	}
	
	public Panel getPanel() {
		return panel;
	}
	
	public void readData(BookingProposalVo bookingProposalVo){
//		panel.clear();
//		items.clear();
		MyWebApp.rootLogger.log(Level.SEVERE, "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		int rowNumb = 2;
		for(PostVo postvo : bookingProposalVo.getPosts()){
			VerticalPanel verticalPanel = new VerticalPanel();
			verticalPanel.setBorderWidth(1);
//			Label label1 = new Label(postvo.getAuthor().getCaption());//Person
//			verticalPanel.add(label1);
			Label label2 = new Label(postvo.getMessage());
			verticalPanel.add(label2);
//			verticalPanel.add(new Label("---"));
			((FlexTable)panel).setWidget(rowNumb, 0, verticalPanel);
			rowNumb++;
		}
//		VerticalPanel verticalPanel = new VerticalPanel();
//		verticalPanel.add(new Label("test label 1"));
//		verticalPanel.add(new Label("test label 2"));
//		((FlexTable)panel).setWidget(2, 0, verticalPanel);
		
	}
	
	public void writeData(BookingProposalVo bookingProposalVo){
	}
	

//	public void refreshContent (){
//	}

//	public void setPanel(Panel panel) {
//		this.panel = panel;
//	}
	
	

}
