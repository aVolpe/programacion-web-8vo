package py.una.pol.webstock.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Compra
 * 
 */
@Entity
public class Compra extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Proveedor proveedor;

	@OneToMany(mappedBy = "compra")
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

}
