package py.com.pg.webstock.ejb.ws;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for pagoDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="pagoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPago" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idCliente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagoDTO", propOrder = { "cliente", "codPago", "estado",
		"fecha", "id", "idCliente", "mensaje", "monto" })
public class PagoDTO {

	protected String cliente;
	protected int codPago;
	protected boolean estado;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fecha;
	protected int id;
	protected int idCliente;
	protected String mensaje;
	protected Double monto;

	/**
	 * Gets the value of the cliente property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * Sets the value of the cliente property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCliente(String value) {
		this.cliente = value;
	}

	/**
	 * Gets the value of the codPago property.
	 * 
	 */
	public int getCodPago() {
		return codPago;
	}

	/**
	 * Sets the value of the codPago property.
	 * 
	 */
	public void setCodPago(int value) {
		this.codPago = value;
	}

	/**
	 * Gets the value of the estado property.
	 * 
	 */
	public boolean isEstado() {
		return estado;
	}

	/**
	 * Sets the value of the estado property.
	 * 
	 */
	public void setEstado(boolean value) {
		this.estado = value;
	}

	/**
	 * Gets the value of the fecha property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * Sets the value of the fecha property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFecha(XMLGregorianCalendar value) {
		this.fecha = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 */
	public void setId(int value) {
		this.id = value;
	}

	/**
	 * Gets the value of the idCliente property.
	 * 
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * Sets the value of the idCliente property.
	 * 
	 */
	public void setIdCliente(int value) {
		this.idCliente = value;
	}

	/**
	 * Gets the value of the mensaje property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Sets the value of the mensaje property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMensaje(String value) {
		this.mensaje = value;
	}

	/**
	 * Gets the value of the monto property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getMonto() {
		return monto;
	}

	/**
	 * Sets the value of the monto property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setMonto(Double value) {
		this.monto = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("{\n");
			for (Field f : getClass().getDeclaredFields()) {
				if (Modifier.isFinal(f.getModifiers()))
					continue;
				f.setAccessible(true);
				sb.append("\t");
				sb.append(f.getName());
				sb.append(" : ");
				sb.append(f.get(this));
				sb.append(",\n");
			}
			sb.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
