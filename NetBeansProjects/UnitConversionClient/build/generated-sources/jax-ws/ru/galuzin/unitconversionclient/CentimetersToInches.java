
package ru.galuzin.unitconversionclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for centimetersToInches complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="centimetersToInches">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="centimeters" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "centimetersToInches", propOrder = {
    "centimeters"
})
public class CentimetersToInches {

    protected double centimeters;

    /**
     * Gets the value of the centimeters property.
     * 
     */
    public double getCentimeters() {
        return centimeters;
    }

    /**
     * Sets the value of the centimeters property.
     * 
     */
    public void setCentimeters(double value) {
        this.centimeters = value;
    }

}
