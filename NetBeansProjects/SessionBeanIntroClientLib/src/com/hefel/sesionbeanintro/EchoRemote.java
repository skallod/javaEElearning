/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hefel.sesionbeanintro;

import javax.ejb.Remote;

/**
 *
 * @author galuzin
 */
@Remote
public interface EchoRemote {

    String echo(final String saying);
    
}
