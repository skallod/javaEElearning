/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gridnine.bof.server.aeroexpress;

import java.util.List;
import ru.lynx.ticket.model.AEXDocumentType;
import ru.lynx.ticket.model.AEXPersonalInfoType;
import ru.lynx.ticket.model.PersonalInfoDocumentTypes;
import ru.lynx.ticket.model.PersonalInfoWrapperType;
import ru.lynx.ticket.model.RequestTicketRequestItem2;
import ru.lynx.ticket.model.RequestTicketsRequest2;
import ru.lynx.ticket.model.RequestTicketsResponse;
import ru.lynx.ticket.service.TicketWsException_Exception;

/**
 *
 * @author galuzin
 */
public class AeroexpressClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        PersonalInfoDocumentTypes personalInfoDocumentTypes =  listPersonalInfoDocumentTypes(null);
        for(AEXDocumentType aEXDocumentType : personalInfoDocumentTypes.getDocumentTypes().getDocumentType()){
            System.out.println(new StringBuilder().append(aEXDocumentType.getDocTypeName()).append(";").append(aEXDocumentType.getDocTypeId()));
        }
        RequestTicketsRequest2 request = new RequestTicketsRequest2();
        request.setDepartDate("2017-01-19");//"yyyy-MM-dd"
        RequestTicketsRequest2.Items items = new RequestTicketsRequest2.Items();
        request.setItems(items);
        List<RequestTicketRequestItem2> itemsType = items.getItem();
        for (int i = 0; i < 1; i++) {
            RequestTicketRequestItem2 itemType =
                new RequestTicketRequestItem2();
            itemsType.add(itemType);
        }
        request.setMenuId(1);
        request.setOrderType(25);
                       
        PersonalInfoWrapperType personalInfoWrapperType = new PersonalInfoWrapperType();
        {
        AEXPersonalInfoType aEXPersonalInfoType =  new AEXPersonalInfoType();
        aEXPersonalInfoType.setDocType("RUSSIAN PASSPORT");
        aEXPersonalInfoType.setDocNumber("4306488603");
        aEXPersonalInfoType.setFirstName("Leonid");
        aEXPersonalInfoType.setPatronymicName("Aleksandrovich");
        aEXPersonalInfoType.setSurname("Galuzin");
        personalInfoWrapperType.getPersonalInfo().add(aEXPersonalInfoType);
        }
//        {
//        AEXPersonalInfoType aEXPersonalInfoType =  new AEXPersonalInfoType();
//        aEXPersonalInfoType.setDocType("RUSSIAN PASSPORT");
//        aEXPersonalInfoType.setDocNumber("4306488604");
//        aEXPersonalInfoType.setFirstName("Anastasiya");
//        aEXPersonalInfoType.setPatronymicName("Aleksandrovich");
//        aEXPersonalInfoType.setSurname("Galuzina");
//        personalInfoWrapperType.getPersonalInfo().add(aEXPersonalInfoType);
//        }
        
        
        RequestTicketsResponse requestTicketsResponse = requestTickets3(null, request, personalInfoWrapperType
                , "leonid.galuzin@gridnine.com", "89267673098");
        System.out.println("response "+requestTicketsResponse);
    }

    private static RequestTicketsResponse requestTickets3(java.lang.String language, ru.lynx.ticket.model.RequestTicketsRequest2 request
        , ru.lynx.ticket.model.PersonalInfoWrapperType personalInfoWrapper, java.lang.String email, java.lang.String mobile) throws TicketWsException_Exception {
        ru.lynx.ticket.service.TicketServiceService service = new ru.lynx.ticket.service.TicketServiceService();
        ru.lynx.ticket.service.TicketServiceServicePortType port = service.getTicketServiceServicePort();
        return port.requestTickets3(language, request, personalInfoWrapper, email, mobile);
    }

    private static PersonalInfoDocumentTypes listPersonalInfoDocumentTypes(java.lang.String language) throws TicketWsException_Exception {
        ru.lynx.ticket.service.TicketServiceService service = new ru.lynx.ticket.service.TicketServiceService();
        ru.lynx.ticket.service.TicketServiceServicePortType port = service.getTicketServiceServicePort();
        return port.listPersonalInfoDocumentTypes(language);
    }
    
}
