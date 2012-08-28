package py.com.pg.webstock.entities;

import java.io.Serializable;
import javax.persistence.*;

import py.com.pg.webstock.entities.BaseEntity;

/**
 * Entity implementation class for Entity: DetalleVenta
 * 
 */
@Entity
public class DetalleVenta extends BaseEntity implements Serializable {

	@ManyToOne
	private Producto producto;


	@ManyToOne
	private Venta venta;
	
	private Double precio;
	private Long cantidad;

	private static final long serialVersionUID = 1L;

	public DetalleVenta() {
		super();
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}
