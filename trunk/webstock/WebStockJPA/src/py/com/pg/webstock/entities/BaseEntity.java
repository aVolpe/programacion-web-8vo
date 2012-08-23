package py.com.pg.webstock.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: BaseEntity
 * 
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private static final long serialVersionUID = 1L;

	public BaseEntity() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
