package py.com.pg.webstock.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProveedorService {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

}
