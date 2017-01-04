
package ru.galuzin.unitconversionclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.galuzin.unitconversionclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloResponse_QNAME = new QName("http://webservices.galuzin.ru/", "helloResponse");
    private final static QName _InchesToCentimetersResponse_QNAME = new QName("http://webservices.galuzin.ru/", "inchesToCentimetersResponse");
    private final static QName _CentimetersToInchesResponse_QNAME = new QName("http://webservices.galuzin.ru/", "centimetersToInchesResponse");
    private final static QName _InchesToCentimeters_QNAME = new QName("http://webservices.galuzin.ru/", "inchesToCentimeters");
    private final static QName _Hello_QNAME = new QName("http://webservices.galuzin.ru/", "hello");
    private final static QName _CentimetersToInches_QNAME = new QName("http://webservices.galuzin.ru/", "centimetersToInches");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.galuzin.unitconversionclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InchesToCentimetersResponse }
     * 
     */
    public InchesToCentimetersResponse createInchesToCentimetersResponse() {
        return new InchesToCentimetersResponse();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link CentimetersToInches }
     * 
     */
    public CentimetersToInches createCentimetersToInches() {
        return new CentimetersToInches();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link InchesToCentimeters }
     * 
     */
    public InchesToCentimeters createInchesToCentimeters() {
        return new InchesToCentimeters();
    }

    /**
     * Create an instance of {@link CentimetersToInchesResponse }
     * 
     */
    public CentimetersToInchesResponse createCentimetersToInchesResponse() {
        return new CentimetersToInchesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InchesToCentimetersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "inchesToCentimetersResponse")
    public JAXBElement<InchesToCentimetersResponse> createInchesToCentimetersResponse(InchesToCentimetersResponse value) {
        return new JAXBElement<InchesToCentimetersResponse>(_InchesToCentimetersResponse_QNAME, InchesToCentimetersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CentimetersToInchesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "centimetersToInchesResponse")
    public JAXBElement<CentimetersToInchesResponse> createCentimetersToInchesResponse(CentimetersToInchesResponse value) {
        return new JAXBElement<CentimetersToInchesResponse>(_CentimetersToInchesResponse_QNAME, CentimetersToInchesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InchesToCentimeters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "inchesToCentimeters")
    public JAXBElement<InchesToCentimeters> createInchesToCentimeters(InchesToCentimeters value) {
        return new JAXBElement<InchesToCentimeters>(_InchesToCentimeters_QNAME, InchesToCentimeters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CentimetersToInches }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.galuzin.ru/", name = "centimetersToInches")
    public JAXBElement<CentimetersToInches> createCentimetersToInches(CentimetersToInches value) {
        return new JAXBElement<CentimetersToInches>(_CentimetersToInches_QNAME, CentimetersToInches.class, null, value);
    }

}
