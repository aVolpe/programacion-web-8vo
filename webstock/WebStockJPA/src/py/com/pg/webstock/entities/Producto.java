package py.com.pg.webstock.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
public class Producto extends BaseEntity implements Serializable {

	
	private String nombre;
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
   
}
