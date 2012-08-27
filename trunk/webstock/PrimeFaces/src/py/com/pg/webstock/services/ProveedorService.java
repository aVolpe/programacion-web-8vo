package py.com.pg.webstock.services;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class ProveedorService {

	@PersistenceContext(unitName = "WebStockJPA")
	EntityManager em;

	public ProveedorService() {
		// TODO Auto-generated constructor stub
		Persistence.createEntityManagerFactory("WebStockJPA").createEntityManager();
	}
}
