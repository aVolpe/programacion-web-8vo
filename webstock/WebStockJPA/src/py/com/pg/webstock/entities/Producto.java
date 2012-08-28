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
	private Long cantidad;
	@ManyToOne
	private Proveedor proveedor;

	private Double precioCompra;
	private Double precioVenta;

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

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

}
