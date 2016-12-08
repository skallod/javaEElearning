/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hefel.sesionbeanintro;

import javax.ejb.Stateless;

/**
 *
 * @author galuzin
 */
@Stateless
public class Echo implements EchoRemote {

    @Override
    public String echo(final String saying) {
        return "echo: "+saying;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
