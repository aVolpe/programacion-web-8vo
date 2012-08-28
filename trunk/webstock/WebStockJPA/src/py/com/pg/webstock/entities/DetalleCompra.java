package py.com.pg.webstock.entities;

import java.io.Serializable;
import javax.persistence.*;
import py.com.pg.webstock.entities.BaseEntity;

/**
 * Entity implementation class for Entity: DetalleCompra
 * 
 */
@Entity
public class DetalleCompra extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Producto producto;

	@ManyToOne
	private Compra compra;

	private Long cantidad;

	private Double precio;

	public DetalleCompra() {
		super();
	}


	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}
