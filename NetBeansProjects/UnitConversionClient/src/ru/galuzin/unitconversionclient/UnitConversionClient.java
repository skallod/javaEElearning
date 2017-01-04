/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.galuzin.unitconversionclient;

/**
 *
 * @author User
 */
public class UnitConversionClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double inches = centimetersToInches(10);
        System.out.println("inches "+inches);
    }

    private static double centimetersToInches(double centimeters) {
        ru.galuzin.unitconversionclient.UnitConversion_Service service = new ru.galuzin.unitconversionclient.UnitConversion_Service();
        ru.galuzin.unitconversionclient.UnitConversion port = service.getUnitConversionPort();
        return port.centimetersToInches(centimeters);
    }
    
}
