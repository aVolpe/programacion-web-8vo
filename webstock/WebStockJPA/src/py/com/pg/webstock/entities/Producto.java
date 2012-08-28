package py.com.pg.webstock.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity implementation class for Entity: Producto
 * 
 */
@Entity
public class Producto extends BaseEntity implements Serializable {

	private String nombre;
	private int cantidad;
	@ManyToOne
	private Proveedor proveedor;

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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}
