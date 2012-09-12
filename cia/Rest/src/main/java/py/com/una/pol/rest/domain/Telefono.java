package py.com.una.pol.rest.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Telefono
 * 
 */
@Entity
public class Telefono extends EntidadBase implements Serializable {

	@ManyToOne
	Persona persona;

	@OneToMany(mappedBy = "telefono")
	List<Llamada> llamadas;

	private String numero;
	private static final long serialVersionUID = 1L;

	public Telefono() {
		super();
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Llamada> getLlamadas() {
		return llamadas;
	}

	public void setLlamadas(List<Llamada> llamadas) {
		this.llamadas = llamadas;
	}

}
