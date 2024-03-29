/**
 *  BidManager.java
 *  EJB 3 in Action
 *  Book: http://manning.com/panda2/
 *  Code: http://code.google.com/p/action-bazaar/
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.actionbazaar.buslogic;

import java.util.List;

import com.actionbazaar.account.Bidder;
import com.actionbazaar.model.Bid;
import com.actionbazaar.model.CreditCard;
import com.actionbazaar.model.Item;

/**
 * BidManager interface
 */
public interface BidManager {
    
    /**
     * Places a snag-it order.
     * @param item - item
     * @param bidder - bidder
     * @param card - credit card to be used
     */
    void placeSnagItOrder(Item item, Bidder bidder, CreditCard card);
    public void cancelBid(Bid bid);
    public List<Bid> getBids(Item item);
}
