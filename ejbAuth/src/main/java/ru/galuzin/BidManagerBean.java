package ru.galuzin;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by galuzin on 22.11.2016.
 */
@DeclareRoles({"BIDDER", "CSR", "ADMIN"})
@Stateless(name = "BidManager")
public class BidManagerBean {
    @RolesAllowed({"CSR","ADMIN"})
    public void cancelBid(Bid bid) {

    }
    @PermitAll
    public List<Bid> getBids(Item item) {
        return item.getBids();
    }
    /*@Interceptors(SecurityInterceptor.class)
    public void cancelBid(Bid bid) {}*/
}
