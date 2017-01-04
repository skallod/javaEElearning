/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.webservices;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author User
 */
@WebService(serviceName = "UnitConversion")
public class UnitConversion {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Операция веб-службы
     */
    @WebMethod(operationName = "inchesToCentimeters")
    public double inchesToCentimeters(@WebParam(name = "inches") double inches) {
        double cantimeters = inches/2.54;
        return cantimeters;
    }

    /**
     * Операция веб-службы
     */
    @WebMethod(operationName = "centimetersToInches")
    public double centimetersToInches(@WebParam(name = "centimeters") double centimeters) {
        double inches = centimeters/2.54;
        return inches;
    }
}
