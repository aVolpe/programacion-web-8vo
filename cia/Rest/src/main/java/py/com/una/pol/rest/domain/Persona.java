package py.com.una.pol.rest.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Persona
 * 
 */
@Entity
public class Persona extends EntidadBase implements Serializable {

	@OneToMany(mappedBy = "persona")
	List<Telefono> telefonos;
	String nombre;
	String ruc;

	private static final long serialVersionUID = 1L;

	public Persona() {
		super();
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

}
