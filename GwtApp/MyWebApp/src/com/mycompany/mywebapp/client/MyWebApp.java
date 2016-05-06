package com.mycompany.mywebapp.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mycompany.mywebapp.shared.BookingProposalVoCreator;
import com.mycompany.mywebapp.shared.FieldVerifier;
import com.mycompany.mywebapp.shared.vo.proposal.BookingProposalVo;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyWebApp implements EntryPoint {
	
	public static final Logger rootLogger = Logger.getLogger("");
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    final Label errorLabel = new Label();

    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);
    
    final TextBox testField = new TextBox();
    testField.setText("TEST TEST");
    RootPanel.get().add(testField);

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
    
    
    ////////// NEW
    
    // Create a Flex Table
    final FlexTable flexTable = new FlexTable();
    FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
    flexTable.addStyleName("flexTable");
    flexTable.setWidth("32em");
    flexTable.setCellSpacing(5);
    flexTable.setCellPadding(3);
    cellFormatter.setHorizontalAlignment(
    0, 1, HasHorizontalAlignment.ALIGN_LEFT);
    flexTable.setHTML(0, 0, "This is a MEGA FlexTable that supports"
    +" <b>colspans</b> and <b>rowspans</b>."
    +" You can use it to format your page"
    +" or as a special purpose table.");
    cellFormatter.setColSpan(0, 0, 2);
    Button addRowButton = new Button("Add a Row"); 
    addRowButton.addClickHandler(new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          addRow(flexTable);
       }
    });
    addRowButton.addStyleName("fixedWidthButton");
    Button removeRowButton = new Button("Remove a Row"); 
    removeRowButton.addClickHandler(new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          removeRow(flexTable);
       }
    });
    removeRowButton.addStyleName("fixedWidthButton");
    VerticalPanel buttonPanel = new VerticalPanel();
    buttonPanel.setStyleName("flexTable-buttonPanel");
    buttonPanel.add(addRowButton);
    buttonPanel.add(removeRowButton);
    flexTable.setWidget(0, 1, buttonPanel);
    VerticalPanel tempPan1 = (VerticalPanel)flexTable.getWidget(0, 1);
    cellFormatter.setVerticalAlignment(0, 1, 
    HasVerticalAlignment.ALIGN_TOP);
    addRow(flexTable);
    addRow(flexTable);
    RootPanel.get().add(flexTable);
    
    Panel panel3 = initBookingProposalPanel();
    
//    VerticalPanel customLogArea = new VerticalPanel();	   
//    rootLogger.addHandler(new HasWidgetsLogHandler(customLogArea));
//    RootPanel.get().add(customLogArea);
//    rootLogger.log(Level.INFO, "TTTTTTTTTTTTTTTT");
    
    DecoratedTabPanel tab;
    tab = new DecoratedTabPanel();
    tab.setAnimationEnabled(false);
    tab.clear();
    tab.setSize("100%", " 100%");
    tab.add(panel3, "Main info");
    //tab.add(getDocumentGrid(), "Документы");
    
    RootPanel.get().add(tab);

  }
  
  private Panel initBookingProposalPanel() {
	  BookingProposalPanel bookingProposalPanel = new BookingProposalPanel();
	  BookingProposalVo bookingProposalVo = BookingProposalVoCreator.create();
	  bookingProposalPanel.readData(bookingProposalVo);
	  return bookingProposalPanel.getPanel();
	  //RootPanel.get().add(bookingProposalPanel.getPanel());
}

/**
  * Add a row to the flex table.
  */
  private void addRow(FlexTable flexTable) {
     int numRows = flexTable.getRowCount();
     flexTable.setWidget(numRows, 0, 
     new Image("http://www.tutorialspoint.com/images/gwt-mini.png"));
     flexTable.setWidget(numRows, 1, 
     new Image("http://www.tutorialspoint.com/images/gwt-mini.png"));
     flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
  }

  /**
  * Remove a row from the flex table.
  */
  private void removeRow(FlexTable flexTable) {
     int numRows = flexTable.getRowCount();
     if (numRows > 1) {
        flexTable.removeRow(numRows - 1);
        flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows - 1);
     }
  }
}
