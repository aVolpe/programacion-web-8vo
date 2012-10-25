package py.com.pg.webstock.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Check;

/**
 * Entity implementation class for Entity: Upload
 * 
 */
@Entity
@Check(constraints = "monto >= 0")
public class Pago extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codPago;

	@ManyToOne(optional = false)
	private Cliente cliente;

	private Double monto;
	private Date fecha;

	@OneToOne
	private Transaccion transaccion;

	public Pago() {
		super();
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Date getfecha() {
		return fecha;
	}

	public void setfecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getCodPago() {
		return codPago;
	}

	public void setCodPago(int codPago) {
		this.codPago = codPago;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

}
