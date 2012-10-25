package py.com.pg.webstock.entities;

import java.io.Serializable;
import javax.persistence.*;
import py.com.pg.webstock.entities.BaseEntity;

/**
 * Entity implementation class for Entity: Transaccion
 * 
 */
@Entity
public class Transaccion extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	String mensaje;

	boolean exito;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

}
