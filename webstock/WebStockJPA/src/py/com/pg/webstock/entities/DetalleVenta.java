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

	private static final long serialVersionUID = 1L;

	public DetalleVenta() {
		super();
	}

}
