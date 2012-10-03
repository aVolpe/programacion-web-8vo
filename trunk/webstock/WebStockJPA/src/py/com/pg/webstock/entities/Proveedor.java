package py.com.pg.webstock.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Proveedor
 * 
 */
@Entity
public class Proveedor extends BaseEntity implements Serializable {

	private String nombre;
	private String telefono;
	private String ruc;
	private Date fechaAlta;

	@OneToMany(mappedBy = "proveedor", fetch = FetchType.EAGER)
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
		return getNombre();
	}
}
