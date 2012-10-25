
package py.com.pg.webstock.ejb.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the py.com.pg.webstock.ejb.ws package. 
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

    private final static QName _AgregarPagosResponse_QNAME = new QName("http://ws.ejb.webstock.pg.com.py/", "agregarPagosResponse");
    private final static QName _AgregarPagos_QNAME = new QName("http://ws.ejb.webstock.pg.com.py/", "agregarPagos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: py.com.pg.webstock.ejb.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AgregarPagos }
     * 
     */
    public AgregarPagos createAgregarPagos() {
        return new AgregarPagos();
    }

    /**
     * Create an instance of {@link AgregarPagosResponse }
     * 
     */
    public AgregarPagosResponse createAgregarPagosResponse() {
        return new AgregarPagosResponse();
    }

    /**
     * Create an instance of {@link PagoDTO }
     * 
     */
    public PagoDTO createPagoDTO() {
        return new PagoDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgregarPagosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb.webstock.pg.com.py/", name = "agregarPagosResponse")
    public JAXBElement<AgregarPagosResponse> createAgregarPagosResponse(AgregarPagosResponse value) {
        return new JAXBElement<AgregarPagosResponse>(_AgregarPagosResponse_QNAME, AgregarPagosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgregarPagos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ejb.webstock.pg.com.py/", name = "agregarPagos")
    public JAXBElement<AgregarPagos> createAgregarPagos(AgregarPagos value) {
        return new JAXBElement<AgregarPagos>(_AgregarPagos_QNAME, AgregarPagos.class, null, value);
    }

}
