package py.una.pol.webstock.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: Producto
 * 
 */
@Entity
public class Producto extends BaseEntity implements Serializable {

	private String nombre;
	private int cantidad;

	private static final long serialVersionUID = 1L;

	public Producto() {
		super();
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
