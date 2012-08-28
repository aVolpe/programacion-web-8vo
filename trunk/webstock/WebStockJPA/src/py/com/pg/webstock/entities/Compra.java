package py.com.pg.webstock.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Compra
 * 
 */
@Entity
public class Compra extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fecha;
	
	private String nroFactura;

	private Double total;
	
	@ManyToOne
	private Proveedor proveedor;

	@OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
	private List<DetalleCompra> detalles;

	public Compra() {
		super();
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCompra> detalles) {
		this.detalles = detalles;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

}
