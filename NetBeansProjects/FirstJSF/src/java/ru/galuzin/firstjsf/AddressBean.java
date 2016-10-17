/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.firstjsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named
@RequestScoped
public class AddressBean {
    
        private String line1;

    /**
     * Get the value of line1
     *
     * @return the value of line1
     */
    public String getLine1() {
        return line1;
    }

    /**
     * Set the value of line1
     *
     * @param line1 new value of line1
     */
    public void setLine1(String line1) {
        this.line1 = line1;
    }
    
        private String line2;

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }


    private String city;

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    
}
