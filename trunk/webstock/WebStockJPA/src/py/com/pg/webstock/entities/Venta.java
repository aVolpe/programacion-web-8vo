package py.com.pg.webstock.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Venta
 * 
 */
@Entity
public class Venta extends BaseEntity implements Serializable {

	@ManyToOne
	private Cliente cliente;
	
	Double total;
	
	@OneToMany(mappedBy = "venta", fetch = FetchType.EAGER)
	private List<DetalleVenta> detalles;

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente c) {
		this.cliente = c;
	}

	private Date fecha;

	private static final long serialVersionUID = 1L;

	public Venta() {
		super();
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

}
