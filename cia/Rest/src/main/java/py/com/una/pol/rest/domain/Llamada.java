package py.com.una.pol.rest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Llamada
 * 
 */
@Entity
public class Llamada extends EntidadBase implements Serializable {

	@ManyToOne
	Telefono telefono;

	private Double duracion;
	private static final long serialVersionUID = 1L;
	private Date fecha;
	
	public Telefono getTelefono() {
		return telefono;
	}

	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}


	public Llamada() {
		super();
	}

	public Double getDuracion() {
		return this.duracion;
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
