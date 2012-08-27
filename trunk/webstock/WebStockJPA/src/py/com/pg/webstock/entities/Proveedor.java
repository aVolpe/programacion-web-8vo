package py.com.pg.webstock.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Proveedor
 * 
 */
@Entity
public class Proveedor extends BaseEntity implements Serializable {

	private String nombre;

	@OneToMany
	private List<Producto> productos;

	private static final long serialVersionUID = 1L;

	public Proveedor() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
