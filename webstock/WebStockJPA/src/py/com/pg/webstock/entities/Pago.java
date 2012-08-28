package py.com.pg.webstock.entities;

import java.io.Serializable;
import javax.persistence.*;
import py.com.pg.webstock.entities.BaseEntity;
import java.util.Date;

/**
 * Entity implementation class for Entity: Upload
 * 
 */
@Entity
public class Pago extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codPago;

	@ManyToOne
	private Cliente cliente;

	private Double monto;
	private Date fecha;

	private String mensaje;
	private int estado;

	@PrePersist
	public void antesDeGuardar() {
		if (monto < 0) {
			mensaje = "Error de monto";
			estado = -1;
			return;
		}
		mensaje = "OK";
		estado = 1;

	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

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

}
